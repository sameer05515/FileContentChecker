package com.p.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;

/**
 * Commonly used file utility methods.
 * @author javaguides.net
 *
 */
public class FileUtils {
 /**
     * Given the size of a file outputs as human readable size using SI prefix.
     * <i>Base 1024</i>
     * @param size Size in bytes of a given File.
     * @return SI String representing the file size (B,KB,MB,GB,TB).
     */
    public static String readableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[] {"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int)(Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) 
          + " " + units[digitGroups];
    }
    
    /**
     *  Get file extension such as "txt","png","pdf"
     * @param file
     * @return
     */
    public static String getFileExtension(File file){
     String fileName = file.getName();
        if(fileName.lastIndexOf('.') != -1 && fileName.lastIndexOf('.') != 0){
         return fileName.substring(fileName.lastIndexOf('.')+1); 
        }else{
         return "File don't have extension";
        }
    }
    
    /**
     * Check for file extension
     * @param file
     * @param extension
     * @return
     */
    public static boolean hasExtension(String file, String extension) {
        return file.endsWith(extension);
    }
    
    /**
     * get last modified given file date
     * @param file
     * @return
     */
    public static long getLastModifiedDate(File file){
     return file.lastModified();
    }
    
   /**
    * check if a file exists in Java
    * @param file
    * @return
    */
    public static boolean checkFileExist(File file){
     return file.exists();
    }
    
    /**
     * Converts InputStream to a String
     * @param in
     * @return
     * @throws IOException
     */
    public static String convertInputStreamToString(final InputStream in) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = in.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString(StandardCharsets.UTF_8.name());
    }
    
    /**
     * Reads content of a file to a String
     * @param path
     * @return
     * @throws IOException
     */
    public String readFileAsString(Path path) throws IOException {
        return new String(Files.readAllBytes(path));
    }
    
    /**
     * getCurrentWorkingDirectoryPath
     * @return
     */
    public static String getCurrentWorkingDirectoryPath() {
        return FileSystems.getDefault().getPath("").toAbsolutePath().toString();
    }

    /**
     * Returns the value of java.io.tmpdir system property. It appends separator if not present at the end.
     * @return
     */
    public static String tmpDirName() {
        String tmpDirName = System.getProperty("java.io.tmpdir");
        if (!tmpDirName.endsWith(File.separator)) {
            tmpDirName += File.separator;
        }

        return tmpDirName;
    }
    
    /**
     * Returns the path to the system temporary directory.
     *
     * @return the path to the system temporary directory.
     */
    public static String getTempDirectoryPath() {
        return System.getProperty("java.io.tmpdir");
    }
    
    /**
     * Returns the path to the user's home directory.
     *
     * @return the path to the user's home directory.
     *
     * @since 2.0
     */
    public static String getUserDirectoryPath() {
        return System.getProperty("user.home");
    }
    
    
    /**
     * Returns a {@link File} representing the user's home directory.
     *
     * @return the user's home directory.
     *
     * @since 2.0
     */
    public static File getUserDirectory() {
        return new File(getUserDirectoryPath());
    }
    
    /**
     * Converts a Collection containing java.io.File instanced into array
     * representation. This is to account for the difference between
     * File.listFiles() and FileUtils.listFiles().
     *
     * @param files a Collection containing java.io.File instances
     * @return an array of java.io.File
     */
    public static File[] convertFileCollectionToFileArray(final Collection<File> files) {
        return files.toArray(new File[files.size()]);
    }

    /**
     * Gets the extension of a file name, like ".png" or ".jpg".
     *
     * @param uri
     * @return Extension including the dot("."); "" if there is no extension;
     *         null if uri was null.
     */
    public static String getExtension(String uri) {
        if (uri == null) {
            return null;
        }

        int dot = uri.lastIndexOf(".");
        if (dot >= 0) {
            return uri.substring(dot);
        } else {
            // No extension.
            return "";
        }
    }
    
    /**
     * Compares the contents of two files to determine if they are equal or not.
     * <p>
     * This method checks to see if the two files are different lengths
     * or if they point to the same file, before resorting to byte-by-byte
     * comparison of the contents.
     * <p>
     * Code origin: Avalon
     *
     * @param file1 the first file
     * @param file2 the second file
     * @return true if the content of the files are equal or they both don't
     * exist, false otherwise
     * @throws IOException in case of an I/O error
     */
    public static boolean contentEquals(final File file1, final File file2) throws IOException {
        final boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }

        if (!file1Exists) {
            // two not existing files are equal
            return true;
        }

        if (file1.isDirectory() || file2.isDirectory()) {
            // don't want to compare directory contents
            throw new IOException("Can't compare directories, only files");
        }

        if (file1.length() != file2.length()) {
            // lengths differ, cannot be equal
            return false;
        }

        if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
            // same file
            return true;
        }

        try (InputStream input1 = new FileInputStream(file1);
             InputStream input2 = new FileInputStream(file2)) {
            return IOUtils.contentEquals(input1, input2);
        }
    }
    
    /**
     * Compares the contents of two files to determine if they are equal or not.
     * <p>
     * This method checks to see if the two files point to the same file,
     * before resorting to line-by-line comparison of the contents.
     * <p>
     *
     * @param file1       the first file
     * @param file2       the second file
     * @param charsetName the character encoding to be used.
     *                    May be null, in which case the platform default is used
     * @return true if the content of the files are equal or neither exists,
     * false otherwise
     * @throws IOException in case of an I/O error
     * @see IOUtils#contentEqualsIgnoreEOL(Reader, Reader)
     * @since 2.2
     */
    public static boolean contentEqualsIgnoreEOL(final File file1, final File file2, final String charsetName)
            throws IOException {
        final boolean file1Exists = file1.exists();
        if (file1Exists != file2.exists()) {
            return false;
        }

        if (!file1Exists) {
            // two not existing files are equal
            return true;
        }

        if (file1.isDirectory() || file2.isDirectory()) {
            // don't want to compare directory contents
            throw new IOException("Can't compare directories, only files");
        }

        if (file1.getCanonicalFile().equals(file2.getCanonicalFile())) {
            // same file
            return true;
        }

        try (Reader input1 = charsetName == null
                                 ? new InputStreamReader(new FileInputStream(file1), Charset.defaultCharset())
                                 : new InputStreamReader(new FileInputStream(file1), charsetName);
             Reader input2 = charsetName == null
                                 ? new InputStreamReader(new FileInputStream(file2), Charset.defaultCharset())
                                 : new InputStreamReader(new FileInputStream(file2), charsetName)) {
            return IOUtils.contentEqualsIgnoreEOL(input1, input2);
        }
    }
    
    /**
     * Returns the path only (without file name).
     *
     * @param file
     * @return
     */
    public static File getPathWithoutFilename(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // no file to be split off. Return everything
                return file;
            } else {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();

                // Construct path without file name.
                String pathwithoutname = filepath.substring(0,
                        filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length() - 1);
                }
                return new File(pathwithoutname);
            }
        }
        return null;
    }
    
    /**
     * Tests if the specified <code>File</code> is newer than the specified
     * <code>Date</code>.
     *
     * @param file the <code>File</code> of which the modification date
     *             must be compared, must not be {@code null}
     * @param date the date reference, must not be {@code null}
     * @return true if the <code>File</code> exists and has been modified
     * after the given <code>Date</code>.
     * @throws IllegalArgumentException if the file is {@code null}
     * @throws IllegalArgumentException if the date is {@code null}
     */
    public static boolean isFileNewer(final File file, final Date date) {
        if (date == null) {
            throw new IllegalArgumentException("No specified date");
        }
        return isFileNewer(file, date.getTime());
    }

    /**
     * Tests if the specified <code>File</code> is newer than the specified
     * time reference.
     *
     * @param file       the <code>File</code> of which the modification date must
     *                   be compared, must not be {@code null}
     * @param timeMillis the time reference measured in milliseconds since the
     *                   epoch (00:00:00 GMT, January 1, 1970)
     * @return true if the <code>File</code> exists and has been modified after
     * the given time reference.
     * @throws IllegalArgumentException if the file is {@code null}
     */
    public static boolean isFileNewer(final File file, final long timeMillis) {
        if (file == null) {
            throw new IllegalArgumentException("No specified file");
        }
        if (!file.exists()) {
            return false;
        }
        return file.lastModified() > timeMillis;
    }

    /**
     * Join file and extension
     * @param file
     * @param ext
     * @return
     */
    public static String join(String file, String ext) {
        return file + '/' + ext;
    }
    

 /**
  * Check if a directory existss
  * 
  * @param dir
  *            the directory to check
  * @return {@code true} if the {@code dir} exists on the file system
  */
 public static boolean exists(String dir) {
  File f = new File(dir);
  if (f.exists()) {
   return true;
  }
  return false;
 }

 /**
  * A wrapper for {@link java.io.File#renameTo(File)}
  * 
  * @param oldf
  *            the original filename
  * @param newf
  *            the new filename
  * @return {@code true} if the move was successful
  */
 public static boolean move(File oldf, File newf) {
  return oldf.renameTo(newf);
 }

 /**
  * A wrapper for {@link java.io.File#renameTo(File)} which creates new File
  * handles for both args.
  * 
  * @param oldf
  *            the original filename
  * @param newf
  *            the new filename
  * @return {@code true} if the move was successful
  */
 public static boolean move(String oldf, String newf) {
  return new File(oldf).renameTo(new File(newf));
 }

 /**
  * Creates {@code dir} if it doesn't yet exist. A wrapper for
  * {@link java.io.File#mkdir()}
  * 
  * @param dir
  *            the directory to create
  * @return {@code true} if the operation was successful
  */
 public static boolean mkdir(String dir) {
  boolean success = exists(dir) ? true : new File(dir).mkdir();
  return success;
 }

 /**
  * Creates all {@code dir}s in the path as needed. A wrapper for
  * {@link java.io.File#mkdirs()}
  * 
  * @param dir
  *            the path to create
  * @return {@code true} if the operation was successful
  */
 public static boolean mkdirs(String dir) {
  boolean success = exists(dir) ? true : new File(dir).mkdirs();
  return success;
 }

 /**
  * Returns the current timestamp in format {@code yyyyMMddHHmmssSSS}
  * 
  * @return the current timestamp
  * @see java.text.SimpleDateFormat
  */
 public static String getTimeStamp() {
  return new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
 }

 /**
  * Get a list of directory contents
  * 
  * @param d
  *            the directory to list
  * @return a list of directory contents
  */
 public static List<File> getFileList(File d) {
  return getFileList(d, -1);
 }

 /**
  * Gets a recursive list of directory contents
  * 
  * @param d
  *            the directory to interrogate
  * @param depth
  *            the number of level to recurse
  * @return a list of directory contents
  */
 public static List<File> getFileList(File d, int depth) {
  List<File> fList = new ArrayList<>();
  if (d.canRead() && d.isDirectory()) {
   File[] list = d.listFiles();
   for (int i = 0; i < list.length; i++) {
    File f = list[i];
    if (f.isFile()) {
     fList.add(f);
    } else {
     if (depth > 0) {
      fList.addAll(getFileList(f, depth - 1));
     } else if (depth == -1) {
      fList.addAll(getFileList(f, -1));
     }
    }
   }
  }
  return fList;
 }

  /**
    * recursively deletes the path and all it's content and returns true if it succeeds
    * Note that the content could be partially deleted and the method return false
    *
    * @param path the path to delete
    * @return true if the path was deleted
    */
   public static boolean forceDeletePath(File path) {
     if (path == null) {
       return false;
     }
     if (path.exists() && path.isDirectory()) {
       File[] files = path.listFiles();
       for (File file : files) {
         if (file.isDirectory()) {
           forceDeletePath(file);
         } else {
           file.delete();
         }
       }
     }
     return path.delete();
   }

}