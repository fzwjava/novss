package cn.com.siemens.novel.util;

import java.io.FilterInputStream;
import java.io.InputStream;

public class FlowMonitorInputStream extends FilterInputStream{

	protected FlowMonitorInputStream(InputStream in) {
		super(in);
	}
	
	/*@Override
	public int read() throws IOException {
		System.out.println("read(1)");
		return super.read();
	}
	@Override
	public int read(byte[] b) throws IOException {
		System.out.println("read(2)");
		return super.read(b);
	}
	@Override
	public int read(byte[] b, int off, int len) throws IOException {
		System.out.println("read(3)");
		return super.read(b, off, len);
	}*/

}
