package gov.samhsa.acs.common.xdm;

import gov.samhsa.acs.common.RandomGenerator;
import gov.samhsa.acs.common.util.ArrayHelper;
import gov.samhsa.acs.common.xdm.XdmZipUtils;

import java.io.IOException;
import org.apache.commons.lang.ArrayUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class XdmZipUtilsTests {

	private static String xslFile;
	private static byte[] kekEncryptionKey;
	private static byte[] kekMaskingKey;
	private static String C32File;
	private static String indexFile;
	private static String readMeFile;
	private static String metadataFile;
	private static byte[] zip;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		kekEncryptionKey = RandomGenerator.randomBytes(50);
		kekMaskingKey = RandomGenerator.randomBytes(50);
		xslFile = RandomGenerator.randomString();
		C32File = RandomGenerator.randomString();
		indexFile = RandomGenerator.randomString();
		readMeFile = RandomGenerator.randomString();
		metadataFile = RandomGenerator.randomString();

		zip = XdmZipUtils.createXDMPackage(metadataFile, xslFile, C32File, indexFile,
				readMeFile, kekMaskingKey, kekEncryptionKey);
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public final void testCreateXDMPackage_containsC32File() throws IOException {
		byte[] bytes = XdmZipUtils.getUtfCustomBytes(C32File);

		String stringFromFactoryBytes = new String(XdmZipUtils.getEntryBytes(zip,
				XdmZipUtils.PATH_C32, bytes.length));

		Assert.assertTrue(ArrayUtils.isEquals(stringFromFactoryBytes, C32File));
	}

	@Test
	public final void testCreateXDMPackage_containsReadMeFile()
			throws IOException {
		byte[] bytes = XdmZipUtils.getUtfCustomBytes(readMeFile);

		String stringFromFactoryBytes = new String(XdmZipUtils.getEntryBytes(zip,
				XdmZipUtils.PATH_README, bytes.length));

		Assert.assertTrue(ArrayUtils.isEquals(stringFromFactoryBytes,
				readMeFile));
	}

	@Test
	public final void testCreateXDMPackage_containsIndexFile()
			throws IOException {
		byte[] bytes = XdmZipUtils.getUtfCustomBytes(indexFile);

		String stringFromFactoryBytes = new String(XdmZipUtils.getEntryBytes(zip,
				XdmZipUtils.PATH_INDEX, bytes.length));

		Assert.assertTrue(ArrayUtils
				.isEquals(stringFromFactoryBytes, indexFile));
	}

	@Test
	public final void testCreateXDMPackage_containsXSLFile() throws IOException {
		byte[] bytes = XdmZipUtils.getUtfCustomBytes(xslFile);

		String stringFromFactoryBytes = new String(XdmZipUtils.getEntryBytes(zip,
				XdmZipUtils.PATH_XSL, bytes.length));

		Assert.assertTrue(ArrayUtils.isEquals(stringFromFactoryBytes, xslFile));
	}

	@Test
	public final void testCreateXDMPackage_containsMetadataFile()
			throws IOException {
		byte[] bytes = XdmZipUtils.getUtfCustomBytes(metadataFile);

		String stringFromFactoryBytes = new String(XdmZipUtils.getEntryBytes(zip,
				XdmZipUtils.PATH_METADATA, bytes.length));

		Assert.assertTrue(ArrayUtils.isEquals(stringFromFactoryBytes,
				metadataFile));
	}

	@Test
	public final void testCreateXDMPackage_containsEncryptKey() {
		Assert.assertTrue(ArrayHelper.hasSubArray(zip, kekEncryptionKey));
	}

	@Test
	public final void testCreateXDMPackage_containsMaskKey() {
		Assert.assertTrue(ArrayHelper.hasSubArray(zip, kekMaskingKey));
	}

	@Test
	public final void testCreateXDMPackage_hasContent() {
		Assert.assertTrue(zip.length > 0);
	}
	
	@Test
	public void testCreateXDMPackage_writeZipFile() throws IOException{
		final String ZIP_PATH = "target/out.zip";
		XdmZipUtils.writeZipfile(ZIP_PATH, zip);
	}
}
