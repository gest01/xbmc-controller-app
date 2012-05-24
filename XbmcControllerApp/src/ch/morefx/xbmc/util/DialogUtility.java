package ch.morefx.xbmc.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import ch.morefx.xbmc.R;

public class DialogUtility {
	
	
	public static void showYesNo(Context context, int messageId, int titleId, final DialogInterface.OnClickListener handler){
		String message = context.getResources().getString(messageId);
		String title = context.getResources().getString(titleId);
		showYesNo(context, message, title, handler);
    }
	
	public static void showYesNo(Context context, int messageId, final DialogInterface.OnClickListener handler){
		String message = context.getResources().getString(messageId);
		String title = context.getResources().getString(android.R.string.dialog_alert_title);
		showYesNo(context, message, title, handler);
	}
	
	public static void showYesNo(Context context, String message, String title, final DialogInterface.OnClickListener handler){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
               .setCancelable(false)
               .setTitle(title)
        	   .setIcon(android.R.drawable.ic_menu_help)
               .setPositiveButton(android.R.string.yes, handler)
               .setNegativeButton(android.R.string.no, handler);
        
        AlertDialog alert = builder.create();
        alert.show();
    }
	
	
	public static void showError(Context context, int messageResourceId){
		String errormessage = context.getResources().getString(messageResourceId);
		String title = "Ohh shit";
		showError(context, errormessage, title);
	}
	
	public static void showError(Context context, String errorMessage, String title){
		showError(context, errorMessage, title,  new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
	}
	
	/**
	 * Shows an error dialog with a close button
	 * @param context The current context
	 * @param errorMessage The message to be displayed
	 * @param title The Dialogs Title
	 * @param handler click handler
	 */
	public static void showError(Context context, String errorMessage, String title, final DialogInterface.OnClickListener handler){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(errorMessage).setIcon(android.R.drawable.ic_dialog_alert);
		builder.setTitle(title);
		
		builder.setCancelable(false);
		builder.setNegativeButton(R.string.close, handler);

		AlertDialog alert = builder.create();
		alert.show();
	}
	
	/**
	 * Shows an error dialog with a yes / no option
	 * @param context The current context
	 * @param message The message to be displayed
	 * @param title The Dialogs Title
	 * @param handler click handler
	 */
	public static void showErrorWithYesNo(Context context, String message, String title, final DialogInterface.OnClickListener handler){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
               .setCancelable(false)
               .setTitle(title)
        	   .setIcon(android.R.drawable.ic_dialog_alert)
               .setPositiveButton(android.R.string.yes, handler)
               .setNegativeButton(android.R.string.no, handler);
        
        AlertDialog alert = builder.create();
        alert.show();
    }
	
	public static void showInfo(Context context, int messageId){
		String message = context.getResources().getString(messageId);
		showInfo(context, message, "Info");
	}
	
	public static void showInfo(Context context, int messageId, int titleId){
		String message = context.getResources().getString(messageId);
		String title = context.getResources().getString(titleId);
		showInfo(context, message, title);
	}
	
	public static void showInfo(Context context, String message, String title){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message).setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle(title);
		builder.setNegativeButton(R.string.close, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		AlertDialog alert = builder.create();
		alert.show();
	}
}
