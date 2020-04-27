package com.fr.coverage.git.service.coverage;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.coverage.bean.CodeLine;
import com.fr.coverage.check.CheckService;
import com.fr.coverage.json.bean.JsonInfo;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.Edit;
import org.eclipse.jgit.diff.EditList;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.patch.FileHeader;
import org.eclipse.jgit.patch.HunkHeader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class CoverageService {

    /**
     * 解析diff对象，获取具体到每一行的改动信息的列表
     *
     * @param diffEntryList diff对象列表
     * @param repository    仓库对象
     * @return
     * @throws IOException
     */
    public List<CodeLine> getCodeLineList(List<DiffEntry> diffEntryList, Repository repository) throws IOException {

        List<CodeLine> ansList = new LinkedList<>();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DiffFormatter df = new DiffFormatter(out);

        // 设置为忽略空白字符的解析模式
        df.setDiffComparator(RawTextComparator.WS_IGNORE_ALL);
        df.setRepository(repository);

        for (DiffEntry diffEntry : diffEntryList) {
            df.format(diffEntry);
            FileHeader fileHeader = df.toFileHeader(diffEntry);
            List<HunkHeader> hunks = (List<HunkHeader>) fileHeader.getHunks();
            for (HunkHeader hunkHeader : hunks) {
                EditList editList = hunkHeader.toEditList();
                for (Edit edit : editList) {
                    if (edit.getType() == Edit.Type.REPLACE || edit.getType() == Edit.Type.INSERT) {
                        for (int line = edit.getBeginB() + 1; line <= edit.getEndB(); line++) {
                            ansList.add(new CodeLine(diffEntry.getNewPath(), line));
                        }
                    }
                }
            }
        }
        StaticLog.info(StrUtil.format("Number of lines in the file after comparison : {}", ansList.size()));
        return ansList;
    }

    /**
     * 生成包含基本信息的json对象
     *
     * @param codeLineBeanList 解析的以行为单位的变化的行的对象列表
     * @param checkService     检查接口
     * @return
     */

    public JsonInfo generateCoverageJsonObj(List<CodeLine> codeLineBeanList, CheckService checkService) {
        int validSum = 0, testedSum = 0;
        JsonInfo jsonInfo = new JsonInfo();
        Map<String, String> tmpMap = new TreeMap<>();
        // 开始计算代码的覆盖率
        for (int index = 0; index < codeLineBeanList.size(); index++) {
            CodeLine codeLine = codeLineBeanList.get(index);
            if (checkService.isValid(codeLine)) {
                validSum++;
            }
            if (checkService.isTested(codeLine)) {
                testedSum++;
            }
            if (checkService.isValid(codeLine) && !checkService.isTested(codeLine)) {
                if (tmpMap.containsKey(codeLine.getPath())) {
                    String value = tmpMap.get(codeLine.getPath());
                    tmpMap.put(codeLine.getPath(), value + " " + codeLine.getLine());
                } else {
                    tmpMap.put(codeLine.getPath(), String.valueOf(codeLine.getLine()));
                }
            }
        }
        Map<String, String> detailMap = new TreeMap<>();
        for (Map.Entry<String, String> entry : tmpMap.entrySet()) {
            List<Integer> valueList = Arrays.stream(entry.getValue().split(StrUtil.SPACE)).map(Integer::new).collect(Collectors.toList());
            StringBuffer stringBuffer = new StringBuffer();
            int start = 0, end = 0;
            for (int index = 0; index < valueList.size(); index++) {
                start = valueList.get(index);
                end = start;
                while (index + 1 < valueList.size() && valueList.get(index) == valueList.get(index + 1) - 1) {
                    end++;
                    index++;
                }
                if (start == end) {
                    stringBuffer.append(StrUtil.format("{},", start));
                } else {
                    stringBuffer.append(StrUtil.format("{}-{},", start, end));
                }
            }
            detailMap.put(entry.getKey(), stringBuffer.substring(0, stringBuffer.length() - 1));
        }
        double coverage = validSum == 0 ? 0 : ((double) testedSum) / validSum;
        jsonInfo.setCoverage(coverage);
        jsonInfo.setValidLineNumber(validSum);
        jsonInfo.setTestedLineNumber(testedSum);
        jsonInfo.setDetail(detailMap);
        return jsonInfo;
    }
}
