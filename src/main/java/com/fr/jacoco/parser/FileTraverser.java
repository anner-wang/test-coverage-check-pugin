package com.fr.jacoco.parser;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Hiram
 * @version 5.1.2
 * Created by Hiram on 2019/11/15
 */
public class FileTraverser {
    private File rootDir;
    private List<String> filterList = new ArrayList<>();

    public FileTraverser(File rootDir) {
        this.rootDir = rootDir;
    }


    public List<String> find(String format, boolean isDir) {
        List<String> resultList = new ArrayList<>();
        find(rootDir, format, isDir, resultList);
        return resultList;
    }

    public List<String> find(String prefix, String format) {
        List<String> resultList = new ArrayList<>();
        find(rootDir, prefix, format, resultList);
        return resultList;
    }

    public FileTraverser filter(String... path) {
        this.filterList = Arrays.asList(path);
        return this;
    }

    private boolean needFilter(File file) {
        String path = file.getPath();
        String[] paths = path.replaceAll("\\\\", "/").split("/");
        String name = paths.length > 1 ? paths[paths.length - 1] : paths[0];
        return filterList.contains(name);
    }

    private void find(File dir, String prefix, String format, List<String> resultList) {
        if (dir == null || !dir.isDirectory() || needFilter(dir)) {
            return;
        }
        resultList.addAll(fileList(dir, prefix, format));
        File[] files = dir.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                find(file, prefix, format, resultList);
            }
        }
    }

    private void find(File dir, String format, boolean isDir, List<String> resultList) {
        if (dir == null || needFilter(dir)) {
            return;
        }
        if (dir.isDirectory() == isDir && dir.getPath().endsWith(format)) {
            resultList.add(dir.getPath());
            return;
        }
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (isExpectedDir(file, format, isDir)) {
                    resultList.add(file.getPath());
                } else if (isExpectedFile(file, format, isDir)) {
                    resultList.add(file.getPath());
                } else if (file.isDirectory()) {
                    find(file, format, isDir, resultList);
                }
            }
        }
    }

    private boolean isExpectedDir(File file, String format, boolean isDir) {
        return file.isDirectory() && file.getPath().endsWith(format) && isDir;
    }

    private boolean isExpectedFile(File file, String format, boolean isDir) {
        return !file.isDirectory() && file.getPath().endsWith(format) && !isDir;
    }

    private static List<String> fileList(File rootFile, String prefix, String format) {
        String[] fileList = rootFile.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.startsWith(prefix) && name.endsWith(format);
            }
        });
        List<String> result = new ArrayList<>();
        for (String file : fileList) {
            result.add(rootFile.getPath() + File.separator + file);
        }
        return result;
    }


}
