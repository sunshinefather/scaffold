package util;

import java.io.IOException;
import java.util.List;

import org.xmind.core.Core;
import org.xmind.core.CoreException;
import org.xmind.core.INotes;
import org.xmind.core.IPlainNotesContent;
import org.xmind.core.ISheet;
import org.xmind.core.ITopic;
import org.xmind.core.IWorkbook;
import org.xmind.core.IWorkbookBuilder;

public class XmindUtil {

	public static IWorkbook createWorkbook() {
		IWorkbookBuilder workbookBuilder = Core.getWorkbookBuilder();
		IWorkbook workbook = workbookBuilder.createWorkbook();
		return workbook;
	}

	public static ISheet getDefaultSheet(IWorkbook workbook, String sheetName) {
		// 获得默认画布
		ISheet primarySheet = workbook.getPrimarySheet();
		// 设置画布名称
		primarySheet.setTitleText(sheetName);
		return primarySheet;
	}

	public static ITopic getRootTopic(ISheet sheet, String topicName) {
		// 获得根主题
		ITopic rootTopic = sheet.getRootTopic();
		// 设置根主题的标题
		rootTopic.setTitleText(topicName);
		// 右逻辑图 org.xmind.ui.logic.right
		rootTopic.setStructureClass("org.xmind.ui.logic.right");
		return rootTopic;
	}

	public static ITopic createTopic(IWorkbook workbook, String topicName) {
		// 创建主题
		ITopic topic = workbook.createTopic();
		// 给主题设置标题名称
		topic.setTitleText(topicName);
		//设置折叠
		topic.setFolded(true);
		return topic;
	}
	
	public static void bindTopic(ITopic fatherTopic, ITopic sonTopic) {
		fatherTopic.add(sonTopic);
	}
	
	public static void bindTopics(ITopic fatherTopic, List<ITopic> sonTopics) {
		sonTopics.forEach(son -> fatherTopic.add(son, ITopic.ATTACHED));
	}

	public static INotes topicBindNotes(IWorkbook workbook, ITopic topic, String notesContent) {
		//创建笔记
		IPlainNotesContent plainContent = (IPlainNotesContent) workbook.createNotesContent(INotes.PLAIN);
		plainContent.setTextContent(notesContent);
		INotes notes = topic.getNotes();
		notes.setContent(INotes.PLAIN, plainContent);
		return notes;
	}
	
	/**
	 * 持久化xmind文档
	 * @param workbook  xmind文档对象
	 * @param path 持久化地址
	 * @return
	 * @throws IOException
	 * @throws CoreException IWorkbook
	 * @author sunshine
	 * @date 2022年1月25日
	 */
	public static IWorkbook save(IWorkbook workbook,String path) throws IOException, CoreException {
		workbook.save(path);
		return workbook;
	}
}
