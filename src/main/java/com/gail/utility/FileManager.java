package com.gail.utility;

import java.io.File;
import java.io.FilenameFilter;

public class FileManager {

	public static File getFileImage(final String fileName, final String basePath)
			throws GailNominationServiceException {
		File imageFile = null;
		try {
			FilenameFilter filter = new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.startsWith(fileName + ".");
				}
			};

			File file = new File(basePath);// create the source directory
			File[] listFiles = file.listFiles(filter);
			if (listFiles.length > 0) {
				for (File f : listFiles) {
					if (f.exists()) {
						imageFile = f;
					}
				}
			}

			if (imageFile == null) {
				throw new GailNominationServiceException(
						new ErrorDetails(Constants.FILE_NOT_FOUND, Constants.ERROR_TYPE_CODE_VALIDATION,
								Constants.ERROR_TYPE_VALIDATION, "No Nomination report found for downloading"));
			}

		} catch (Exception ex) {
			ExceptionUtil.handleException(new ErrorDetails(Constants.FILE_DOWNLOAD_ERROR,
					Constants.ERROR_TYPE_CODE_INTERNAL, Constants.ERROR_TYPE_ERROR, "Error while downloaading report."),
					ex, fileName);
		}
		return imageFile;
	}

}
