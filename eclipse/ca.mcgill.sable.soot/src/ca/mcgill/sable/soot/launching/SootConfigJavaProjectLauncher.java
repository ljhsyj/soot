/* Soot - a J*va Optimization Framework
 * Copyright (C) 2003 Jennifer Lhotak
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package ca.mcgill.sable.soot.launching;

import java.util.HashMap;
import java.util.Iterator;

import org.eclipse.jface.action.IAction;
import ca.mcgill.sable.soot.*;
import ca.mcgill.sable.soot.ui.SootConfigManagerDialog;

import org.eclipse.jface.dialogs.*;
//import org.eclipse.jface.viewers.LabelProvider;
//import org.eclipse.ui.dialogs.ElementListSelectionDialog;

/**
 * Launches a saved Soot configuration on the all the
 * class files in the output dir of the selected project
 */
public class SootConfigJavaProjectLauncher extends SootProjectLauncher {

	public void run(IAction action) {
		
		super.run(action);
		
		SootConfigManagerDialog manager = new SootConfigManagerDialog(getWindow().getShell());
		manager.setEclipseDefList(setEclipseDefs());
		manager.setLauncher(this);
		manager.open();
		
		
	}
	
	public void launch(String name, String mainClass) {
		
		IDialogSettings settings = SootPlugin.getDefault().getDialogSettings();
		
		setSootCommandList(new SootCommandList());
		//System.out.println(settings.get(choosen));
		
		// TODO switch these 2 lines
		SootSavedConfiguration ssc = new SootSavedConfiguration(name, settings.getArray(name));
		//SootSavedConfiguration ssc = new SootSavedConfiguration(name, settings.get(name));
		ssc.setEclipseDefs(setRunEclipseDefs());
		
		// TODO swicth these 2 lines
		getSootCommandList().addSingleOpt(ssc.toRunArray());
		//getSootCommandList().addSingleOpt(ssc.toRunString());
		//System.out.println("set SootCommandList");
		
		if ((mainClass == null) || (mainClass.length() == 0)){
			runSootDirectly();
		}
		else {
			runSootDirectly(mainClass);
		}
		runFinish();
	}
	
	private HashMap setEclipseDefs() {
		
		HashMap defs = new HashMap();
		defs.put(LaunchCommands.OUTPUT_DIR, getOutputLocation());
		//System.out.println("setting eclipse defs");
		//System.out.println(getOutputLocation());
		Iterator it = getJavaProcessPath().iterator();
		String cp = (String)it.next();
		while (it.hasNext()){
			cp = cp + getSootClasspath().getSeparator() + (String)it.next();
		}
		cp = cp + getSootClasspath().getSeparator() + getClasspathAppend();
		System.out.println("cp: "+cp);
		defs.put(LaunchCommands.SOOT_CLASSPATH, cp);
		//System.out.println(getSootClasspath().getSootClasspath()+getSootClasspath().getSeparator()+getProcess_path());
		/*Iterator it2 = getJavaProcessPath().iterator();
		String path = "";
		while (it2.hasNext()){
			String next = (String)it2.next();
			if (path.equals("")){
				path = next;
			}
			else {
				path = path + "\r\n" + next;
			}
		}*/
		defs.put(LaunchCommands.PROCESS_PATH, getJavaProcessPath());
		//System.out.println(getProcess_path());
		//System.out.println("presetting process-path"+getProcess_path());
		defs.put(LaunchCommands.KEEP_LINE_NUMBER, new Boolean(true));
		//getSdc().setKeepLineNum();
		//System.out.println("presetting keep line num");
		defs.put(LaunchCommands.XML_ATTRIBUTES, new Boolean(true));
		
		defs.put(LaunchCommands.SRC_PREC, "java");
		//getSdc().setPrintTags();	
		//System.out.println("presetting print tags");
		
		//ssc.setEclipseDefs(defs);
		return defs;
	}
	
	private HashMap setRunEclipseDefs() {
		
		HashMap defs = new HashMap();
		defs.put(LaunchCommands.OUTPUT_DIR, getOutputLocation());
		//System.out.println("setting eclipse defs");
		//System.out.println(getOutputLocation());
		Iterator it = getJavaProcessPath().iterator();
		String cp = (String)it.next();
		while (it.hasNext()){
			cp = cp + getSootClasspath().getSeparator() + (String)it.next();
		}
		cp = cp + getSootClasspath().getSeparator() + getClasspathAppend();
		defs.put(LaunchCommands.SOOT_CLASSPATH, cp);
		//System.out.println(getSootClasspath().getSootClasspath()+getSootClasspath().getSeparator()+getProcess_path());
		Iterator it2 = getJavaProcessPath().iterator();
		String pPath = "";
		while (it2.hasNext()){
			String next = (String)it2.next();
			if (pPath.equals("")){
				pPath = next;
			}
			else {
				pPath = pPath + "\r\n" + next;
			}
			
		}
		defs.put(LaunchCommands.PROCESS_PATH, pPath);// getJavaProcessPath());
		//System.out.println(getProcess_path());
		//System.out.println("presetting process-path"+getProcess_path());
		defs.put(LaunchCommands.KEEP_LINE_NUMBER, new Boolean(true));
		//getSdc().setKeepLineNum();
		//System.out.println("presetting keep line num");
		defs.put(LaunchCommands.XML_ATTRIBUTES, new Boolean(true));
	
		defs.put(LaunchCommands.SRC_PREC, "java");
		//getSdc().setPrintTags();	
		//System.out.println("presetting print tags");
	
		//ssc.setEclipseDefs(defs);
		return defs;
	}
	
	/*private void setEclipseDefs(SootSavedConfiguration ssc) {
		
		HashMap defs = new HashMap();
		defs.put(LaunchCommands.OUTPUT_DIR, getOutputLocation());
		//System.out.println("setting eclipse defs");
		//System.out.println(getOutputLocation());
		
		defs.put(LaunchCommands.SOOT_CLASSPATH, getSootClasspath().getSootClasspath()+getSootClasspath().getSeparator()+getProcess_path());
		//System.out.println(getSootClasspath().getSootClasspath()+getSootClasspath().getSeparator()+getProcess_path());
		
		defs.put(LaunchCommands.PROCESS_PATH, getProcess_path());
		//System.out.println(getProcess_path());
		//System.out.println("presetting process-path"+getProcess_path());
		defs.put(LaunchCommands.KEEP_LINE_NUMBER, new Boolean(true));
		//getSdc().setKeepLineNum();
		//System.out.println("presetting keep line num");
		defs.put(LaunchCommands.XML_ATTRIBUTES, new Boolean(true));
		//getSdc().setPrintTags();	
		//System.out.println("presetting print tags");
		
		ssc.setEclipseDefs(defs);
		//return defs;
	}*/
	
}
