package com.fr.coverage.git.service.coverage;

import cn.hutool.core.util.StrUtil;
import cn.hutool.log.StaticLog;
import com.fr.coverage.bean.CodeLine;
import com.fr.coverage.check.CheckService;
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
import java.util.LinkedList;
import java.util.List;

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
     * 调取接口，计算代码的覆盖率
     *
     * @param codeLineBeanList 封装的存储代码修改信息的对象
     * @param checkService     模拟的检查代码是否被覆盖到的接口
     * @return 最终的代码的覆盖率
     */

    public double calTestCoverage(List<CodeLine> codeLineBeanList, CheckService checkService) {
        // 开始计算有效的覆盖率
        int validSum = 0, testedSum = 0;
        for (CodeLine codeLine : codeLineBeanList) {
            if (checkService.isValid(codeLine)) {
                validSum++;
            }
            if (checkService.isTested(codeLine)) {
                testedSum++;
            }
        }
        if (validSum == 0) {
            return 0;
        }
        return ((double) testedSum) / validSum;
    }
}
