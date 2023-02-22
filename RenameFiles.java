import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RenameFiles {
    public static void main(String[] args) {
        String path = "./";
        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        Map<String, String> chineseToNumMap = new HashMap<>();
        chineseToNumMap.put("一", "01");
        chineseToNumMap.put("二", "02");
        chineseToNumMap.put("三", "03");
		chineseToNumMap.put("四", "04");
		chineseToNumMap.put("五", "05");
		chineseToNumMap.put("六", "06");
		chineseToNumMap.put("七", "07");
		chineseToNumMap.put("八", "08");
		chineseToNumMap.put("九", "09");
		chineseToNumMap.put("十", "10");
        // add more mappings here as needed
        renameFilesInFolder(folder, chineseToNumMap);
    }

    private static void renameFilesInFolder(File folder, Map<String, String> chineseToNumMap) {
        File[] listOfFiles = folder.listFiles();
        for (File file : listOfFiles) {
            if (file.isDirectory()) {
                renameFilesInFolder(file, chineseToNumMap);
            } else {
                String fileName = file.getName();
                String newFileName = replaceChineseNumsWithTwoDigits(fileName, chineseToNumMap);
                if (!fileName.equals(newFileName)) {
                    File newFile = new File(file.getParent(), newFileName);
                    if (file.renameTo(newFile)) {
                        System.out.println("Renamed file " + fileName + " to " + newFileName);
                    } else {
                        System.out.println("Failed to rename file " + fileName);
                    }
                }
            }
        }
    }

    private static String replaceChineseNumsWithTwoDigits(String fileName, Map<String, String> chineseToNumMap) {
        Pattern pattern = Pattern.compile("(第)([一二三四五六七八九十])(章)");
        Matcher matcher = pattern.matcher(fileName);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String chineseNum = matcher.group(2);
            String num = chineseToNumMap.get(chineseNum);
            if (num != null) {
                matcher.appendReplacement(sb, "$1" + num + "$3");
            }
        }
        matcher.appendTail(sb);
        return sb.toString();
    }
}