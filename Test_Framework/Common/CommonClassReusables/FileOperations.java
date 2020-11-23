package CommonClassReusables;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileOperations {

	
	/**
	* <h1>delAllFileInFolder</h1>
	* This is a Method to Delete all Files in a Folder
	* @author  	Jiten Khanna
	* @modified 
	* @version 	1.0
	* @since   	03-31-2020
	* @param   	String folderPath
	* @return  	none
	*/
	
	public static void delAllFileInFolder(String folderPath) throws IOException {
		try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
			paths.forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					try {
						Files.delete(filePath);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(filePath);
				}
			});
		}
	}

}
