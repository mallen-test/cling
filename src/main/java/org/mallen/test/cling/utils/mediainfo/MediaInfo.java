package org.mallen.test.cling.utils.mediainfo;

import org.omg.CORBA.MARSHAL;

import javax.print.attribute.standard.Media;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 读取视频文件属性事例：
 * General
 * Count                                    : 330
 * Count of stream of this kind             : 1
 * Kind of stream                           : General
 * Kind of stream                           : General
 * Stream identifier                        : 0
 * Count of video streams                   : 1
 * Count of audio streams                   : 1
 * Video_Format_List                        : AVC
 * Video_Format_WithHint_List               : AVC
 * Codecs Video                             : AVC
 * Audio_Format_List                        : AAC
 * Audio_Format_WithHint_List               : AAC
 * Audio codecs                             : AAC LC
 * Complete name                            : E:\t\XQDZ8.1080p.BD中英双字[最新电影www.6vhao.tv].mp4
 * Folder name                              : E:\t
 * File name                                : XQDZ8.1080p.BD中英双字[最新电影www.6vhao.tv].mp4
 * File name                                : XQDZ8.1080p.BD中英双字[最新电影www.6vhao.tv]
 * File extension                           : mp4
 * Format                                   : MPEG-4
 * Format                                   : MPEG-4
 * Format/Extensions usually used           : mov mp4 m4v m4a m4b m4p 3ga 3gpa 3gpp 3gp 3gpp2 3g2 k3g jpm jpx mqv ismv isma ismt f4a f4b f4v
 * Commercial name                          : MPEG-4
 * Format profile                           : Base Media
 * Internet media type                      : video/mp4
 * Codec ID                                 : isom
 * Codec ID                                 : isom (isom/avc1)
 * Codec ID/Url                             : http://www.apple.com/quicktime/download/standalone.html
 * CodecID_Compatible                       : isom/avc1
 * Codec                                    : MPEG-4
 * Codec                                    : MPEG-4
 * Codec/Extensions usually used            : mov mp4 m4v m4a m4b m4p 3ga 3gpa 3gpp 3gp 3gpp2 3g2 k3g jpm jpx mqv ismv isma ismt f4a f4b f4v
 * File size                                : 3557476726
 * File size                                : 3.31 GiB
 * File size                                : 3 GiB
 * File size                                : 3.3 GiB
 * File size                                : 3.31 GiB
 * File size                                : 3.313 GiB
 * Duration                                 : 9111680
 * Duration                                 : 2 h 31 min
 * Duration                                 : 2 h 31 min 51 s 680 ms
 * Duration                                 : 2 h 31 min
 * Duration                                 : 02:31:51.680
 * Duration                                 : 02:31:53;20
 * Duration                                 : 02:31:51.680 (02:31:53;20)
 * Overall bit rate mode                    : VBR
 * Overall bit rate mode                    : Variable
 * Overall bit rate                         : 3123443
 * Overall bit rate                         : 3 123 kb/s
 * Frame rate                               : 23.976
 * Frame rate                               : 23.976 FPS
 * Frame count                              : 218460
 * Stream size                              : 4612837
 * Stream size                              : 4.40 MiB (0%)
 * Stream size                              : 4 MiB
 * Stream size                              : 4.4 MiB
 * Stream size                              : 4.40 MiB
 * Stream size                              : 4.399 MiB
 * Stream size                              : 4.40 MiB (0%)
 * Proportion of this stream                : 0.00130
 * HeaderSize                               : 4612761
 * DataSize                                 : 3552863897
 * FooterSize                               : 68
 * IsStreamable                             : Yes
 * Encoded date                             : UTC 2018-03-10 14:04:14
 * Tagged date                              : UTC 2018-03-10 14:04:14
 * File creation date                       : UTC 2018-03-24 09:01:56.294
 * File creation date (local)               : 2018-03-24 17:01:56.294
 * File last modification date              : UTC 2018-03-15 03:32:53.175
 * File last modification date (local)      : 2018-03-15 11:32:53.175
 * <p>
 * Video
 * Count                                    : 344
 * Count of stream of this kind             : 1
 * Kind of stream                           : Video
 * Kind of stream                           : Video
 * Stream identifier                        : 0
 * StreamOrder                              : 0
 * ID                                       : 1
 * ID                                       : 1
 * Format                                   : AVC
 * Format/Info                              : Advanced Video Codec
 * Format/Url                               : http://developers.videolan.org/x264.html
 * Commercial name                          : AVC
 * Format profile                           : High@L4.1
 * Format settings                          : CABAC / 4 Ref Frames
 * Format settings, CABAC                   : Yes
 * Format settings, CABAC                   : Yes
 * Format settings, ReFrames                : 4
 * Format settings, ReFrames                : 4 frames
 * Internet media type                      : video/H264
 * Codec ID                                 : avc1
 * Codec ID/Info                            : Advanced Video Coding
 * Codec                                    : AVC
 * Codec                                    : AVC
 * Codec/Family                             : AVC
 * Codec/Info                               : Advanced Video Codec
 * Codec/Url                                : http://developers.videolan.org/x264.html
 * Codec/CC                                 : avc1
 * Codec profile                            : High@L4.1
 * Codec settings                           : CABAC / 4 Ref Frames
 * Codec settings, CABAC                    : Yes
 * Codec_Settings_RefFrames                 : 4
 * Duration                                 : 9111602
 * Duration                                 : 2 h 31 min
 * Duration                                 : 2 h 31 min 51 s 602 ms
 * Duration                                 : 2 h 31 min
 * Duration                                 : 02:31:51.602
 * Duration                                 : 02:31:53;20
 * Duration                                 : 02:31:51.602 (02:31:53;20)
 * Bit rate                                 : 3000000
 * Bit rate                                 : 3 000 kb/s
 * Maximum bit rate                         : 14635448
 * Maximum bit rate                         : 14.6 Mb/s
 * Width                                    : 1920
 * Width                                    : 1 920 pixels
 * Height                                   : 1076
 * Height                                   : 1 076 pixels
 * Stored_Height                            : 1088
 * Sampled_Width                            : 1920
 * Sampled_Height                           : 1076
 * Pixel aspect ratio                       : 1.000
 * Display aspect ratio                     : 1.784
 * Display aspect ratio                     : 16:9
 * Rotation                                 : 0.000
 * Frame rate mode                          : CFR
 * Frame rate mode                          : Constant
 * Frame rate                               : 23.976
 * Frame rate                               : 23.976 (24000/1001) FPS
 * FrameRate_Num                            : 24000
 * FrameRate_Den                            : 1001
 * Frame count                              : 218460
 * Resolution                               : 8
 * Resolution                               : 8 bits
 * Colorimetry                              : 4:2:0
 * Color space                              : YUV
 * Chroma subsampling                       : 4:2:0
 * Chroma subsampling                       : 4:2:0
 * Bit depth                                : 8
 * Bit depth                                : 8 bits
 * Scan type                                : Progressive
 * Scan type                                : Progressive
 * Interlacement                            : PPF
 * Interlacement                            : Progressive
 * Bits/(Pixel*Frame)                       : 0.061
 * Stream size                              : 3416188689
 * Stream size                              : 3.18 GiB (96%)
 * Stream size                              : 3 GiB
 * Stream size                              : 3.2 GiB
 * Stream size                              : 3.18 GiB
 * Stream size                              : 3.182 GiB
 * Stream size                              : 3.18 GiB (96%)
 * Proportion of this stream                : 0.96028
 * Writing library                          : x264 - core 148 r2744 b97ae06
 * Writing library                          : x264 core 148 r2744 b97ae06
 * Encoded_Library_Name                     : x264
 * Encoded_Library_Version                  : core 148 r2744 b97ae06
 * Encoding settings                        : cabac=1 / ref=2 / deblock=1:0:0 / analyse=0x3:0x113 / me=hex / subme=7 / psy=1 / psy_rd=1.00:0.00 / mixed_ref=1 / me_range=16 / chroma_me=1 / trellis=1 / 8x8dct=1 / cqm=0 / deadzone=21,11 / fast_pskip=1 / chroma_qp_offset=-2 / threads=12 / lookahead_threads=2 / sliced_threads=0 / nr=0 / decimate=1 / interlaced=0 / bluray_compat=0 / constrained_intra=0 / bframes=3 / b_pyramid=2 / b_adapt=1 / b_bias=0 / direct=1 / weightb=1 / open_gop=0 / weightp=1 / keyint=240 / keyint_min=23 / scenecut=40 / intra_refresh=0 / rc_lookahead=30 / rc=2pass / mbtree=1 / bitrate=3000 / ratetol=1.0 / qcomp=0.60 / qpmin=0 / qpmax=69 / qpstep=4 / cplxblur=20.0 / qblur=0.5 / vbv_maxrate=62500 / vbv_bufsize=78125 / nal_hrd=none / filler=0 / ip_ratio=1.40 / aq=1:1.00
 * Encoded date                             : UTC 2018-03-10 14:04:14
 * Tagged date                              : UTC 2018-03-10 14:04:56
 * <p>
 * Audio
 * Count                                    : 275
 * Count of stream of this kind             : 1
 * Kind of stream                           : Audio
 * Kind of stream                           : Audio
 * Stream identifier                        : 0
 * StreamOrder                              : 1
 * ID                                       : 2
 * ID                                       : 2
 * Format                                   : AAC
 * Format/Info                              : Advanced Audio Codec
 * Commercial name                          : AAC
 * Format profile                           : LC
 * Format settings, SBR                     : No (Explicit)
 * Format settings, SBR                     : No (Explicit)
 * Codec ID                                 : mp4a-40-2
 * Codec                                    : AAC LC
 * Codec                                    : AAC LC
 * Codec/Family                             : AAC
 * Codec/CC                                 : 40
 * Duration                                 : 9111680
 * Duration                                 : 2 h 31 min
 * Duration                                 : 2 h 31 min 51 s 680 ms
 * Duration                                 : 2 h 31 min
 * Duration                                 : 02:31:51.680
 * Duration                                 : 02:31:27:21
 * Duration                                 : 02:31:51.680 (02:31:27:21)
 * Bit rate mode                            : VBR
 * Bit rate mode                            : Variable
 * Bit rate                                 : 120000
 * Bit rate                                 : 120 kb/s
 * Maximum bit rate                         : 128160
 * Maximum bit rate                         : 128 kb/s
 * Channel(s)                               : 2
 * Channel(s)                               : 2 channels
 * Channel positions                        : Front: L R
 * Channel positions                        : 2/0/0
 * ChannelLayout                            : L R
 * Samples per frame                        : 1024
 * Sampling rate                            : 48000
 * Sampling rate                            : 48.0 kHz
 * Samples count                            : 437360640
 * Frame rate                               : 46.875
 * Frame rate                               : 46.875 FPS (1024 SPF)
 * Frame count                              : 427110
 * Compression mode                         : Lossy
 * Compression mode                         : Lossy
 * Stream size                              : 136675200
 * Stream size                              : 130 MiB (4%)
 * Stream size                              : 130 MiB
 * Stream size                              : 130 MiB
 * Stream size                              : 130 MiB
 * Stream size                              : 130.3 MiB
 * Stream size                              : 130 MiB (4%)
 * Proportion of this stream                : 0.03842
 * Encoded date                             : UTC 2018-03-10 11:22:57
 * Tagged date                              : UTC 2018-03-10 14:04:56
 * <p>
 * Created By mallen on 2018/4/8 22:33
 **/
public class MediaInfo {
    private static final String file = "E:\\t\\XQDZ8.1080p.BD中英双字[最新电影www.6vhao.tv].mp4";
    private static final String file1 = "E:\\t\\1.Android 5.0 新特性.mp4";
    private static Map<String, Map<String, String>> sectionMap = new HashMap<>();
    private static Map<String, String> infoMap = new HashMap<>();
    public static final String SECTION_NAME_GENERAL = "General";
    public static final String SECTION_NAME_VIDEO = "Video";
    public static final String SECTION_NAME_AUDIO = "Audio";

//    private static Map<String, String> mimeMap;

    public static void main(String[] args) throws IOException, InterruptedException {
        System.out.println(new MediaInfo().getInfoText(file1));
    }
//    static {
//        mimeMap = new HashMap<>();
//        mimeMap.put("MPEG-4", "video/mp4");//.mp4
//        mimeMap.put("3GP Mobile", "video/3gpp");//.3gp
//        mimeMap.put("Flash", "video/x-flv");//.flv
//        mimeMap.put("QuickTime", "video/quicktime");//.mov
//        mimeMap.put("A/V Interleave", "video/x-msvideo");//.avi
//        mimeMap.put("Windows Media", "video/x-ms-wmv");//.wmv
//    }

    public static MediaInfo build(String filePath) {
        try {
            MediaInfo mediaInfo = new MediaInfo();
            mediaInfo.parse(filePath);
            addBasicInfo(filePath);
            return mediaInfo;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void addBasicInfo(String filePath) throws IOException {
//        BasicFileAttributeView view = Files.getFileAttributeView(Paths.get(filePath), BasicFileAttributeView.class);
//        BasicFileAttributes attributes = view.readAttributes();
//        attributes.
    }

    public Map<String, String> getSection(String section) {
        return sectionMap.get(section);
    }

    public String getInfo(String key) {
        return infoMap.get(key);
    }

    public String getMimeType() {
        return sectionMap.get(SECTION_NAME_GENERAL).get("Internet media type");
    }

    public String getVideoMimeType() {
        return sectionMap.get(SECTION_NAME_VIDEO).get("Internet media type");
    }

    /**
     * 获取文件完整路劲
     *
     * @return
     */
    public String getFilePath() {
        return sectionMap.get(SECTION_NAME_GENERAL).get("Complete name");
    }

    /**
     * 获取文件名
     * @return
     */
    public String getFileName() {
        return sectionMap.get(SECTION_NAME_GENERAL).get("File name");
    }

    public String getCreator() {
        return "UNKNOWN";
    }

    public String getWidthHeight() {
        return infoMap.get("Sampled_Width") + "x" + infoMap.get("Sampled_Height");
    }

    public String getDescription() {
        return "";
    }

    /**
     * 获取播放时长，单位毫秒
     *
     * @return
     */
    public long getDuration() {
        return Long.parseLong(infoMap.get("Duration"));
    }

    /**
     * 获取文件总大小，单位字节
     *
     * @return
     */
    public long getSize() {
        return Long.parseLong(infoMap.get("File size"));
    }

    /**
     * 获取文件码率，单位bps，即字节每秒（换算为kb时，可以直接除以1000）
     *
     * @return
     */
    public long getBitRate() {
        return Long.valueOf(infoMap.get("Bit rate"));
    }

    private void parse(String mediaPath) throws IOException, InterruptedException {
        String mediaInfo = getInfoText(mediaPath);
        String[] infoArray = mediaInfo.split(System.getProperty("line.separator"));
        String currentSection = null;
        for (String info : infoArray) {
            if (info.contains(":")) {
                String[] nameValue = info.split("\\s*:\\s*", 2);
                if (nameValue[0].equals("Duration") && !nameValue[1].matches("[0-9]+")) {
                    continue;
                }
                if (nameValue[0].equals("File size") && !nameValue[1].matches("[0-9]+")) {
                    continue;
                }
                if (nameValue[0].equals("Bit rate") && !nameValue[1].matches("[0-9]+")) {
                    continue;
                }
                infoMap.put(nameValue[0], nameValue[1]);
                sectionMap.get(currentSection).put(nameValue[0], nameValue[1]);
            } else {
                sectionMap.put(info, new HashMap<String, String>());
                currentSection = info;
            }
        }
    }

    public String getInfoText(String mediaFile) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder(getMediaInfoCliPath(), mediaFile, "-f");
        processBuilder.redirectErrorStream(true);

        final Process process = processBuilder.start();
        final StringBuilder buffer = new StringBuilder();
        try (Reader reader = new InputStreamReader(process.getInputStream())) {
            for (int i; (i = reader.read()) != -1; ) {
                buffer.append((char) i);
            }
        }

        final int status = process.waitFor();
        if (status == 0) {
            return buffer.toString();
        }

        return null;
    }

    private String getMediaInfoCliPath() throws IOException {
        final Properties properties = new Properties();
        properties.load(new InputStreamReader(MediaInfo.class.getClassLoader().getResourceAsStream("mediainfo.properties"), "UTF-8"));

        final String exePath = properties.getProperty("mediainfo.cli.path");
        if (exePath == null) {
            throw new IOException("The property 'mediainfo.cli.path' is not set");
        }

        final File file = new File(exePath).getAbsoluteFile();
        if (!file.isFile()) {
            throw new FileNotFoundException("The value of the property 'mediainfo.cli.path' does not point to a file");
        }

        return file.getAbsolutePath();
    }

    private MediaInfo() {
    }


}
