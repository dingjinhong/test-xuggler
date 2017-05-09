package xuggler;

import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.event.IAddStreamEvent;
import com.xuggle.xuggler.IRational;
import com.xuggle.xuggler.IStreamCoder;
import com.xuggle.xuggler.ICodec;
public class MyVideoListener extends MediaToolAdapter {
	private Integer width;
	private Integer height;
	private String type;
	public MyVideoListener(Integer aWidth, Integer aHeight,String atype) {
		this.width = aWidth;
		this.height = aHeight;
		this.type=atype;
	}
 
	@Override
	public void onAddStream(IAddStreamEvent event) {
		//System.out.println("1");
		int streamIndex = event.getStreamIndex();
		IStreamCoder streamCoder = event.getSource().getContainer().getStream(streamIndex).getStreamCoder();
		if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {
		} else if (streamCoder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
			if(type.equals("ogg")){
			streamCoder.setFlag(IStreamCoder.Flags.FLAG_QSCALE, false);
			streamCoder.setBitRate(300000);
			streamCoder.setTimeBase(IRational.make(1,25));
			}
			streamCoder.setWidth(width);
			streamCoder.setHeight(height);
			//writer.addVideoStream(streamIndex, streamIndex, width, height);
		}
		super.onAddStream(event);
	}
}
