package fantasticfour.magiceight;

import java.awt.event.ActionEvent;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

//@author A0115693B

public class AutoComplete {
	private static enum Status { 
		INSERT,
		COMPLETION
	};
	public class Autocomplete implements DocumentListener {
		private JTextField commandLine;
		private final List<String> keywords;
		private Status mode = Status.INSERT;

		public Autocomplete(JTextField commandLine, List<String> keywords) {
			this.commandLine = commandLine;
			this.keywords = keywords;
			Collections.sort(keywords);
		}

		@Override
		public void changedUpdate(DocumentEvent event) { 
			
		}

		@Override
		public void removeUpdate(DocumentEvent event) {
			
		}

		@Override
		public void insertUpdate(DocumentEvent event) {
			if (event.getLength() != 1)
				return;

			int position = event.getOffset();
			String content = null;
			try {
				content = commandLine.getText(0, position + 1);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

			// Find where the word starts
			int w;
			for (w = position; w >= 0; w--) {
				if (!Character.isLetter((content.charAt(w)))) {
					break;
				}
			}

			// Too few chars
			if (position - w < 1)
				return;

			String prefix = content.substring(w + 1).toLowerCase();
			int n = Collections.binarySearch(keywords, prefix);
			if (n < 0 && -n <= keywords.size()) {
				String match = keywords.get(-n - 1);
				if (match.startsWith(prefix)) {

					String completion = match.substring(position - w);
					SwingUtilities.invokeLater(new CompletionTask(completion, position + 1));
				}
			} else {
				mode = Status.INSERT;
			}
		}

		public class CommitAction extends AbstractAction {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent event) {
				if (mode == Status.COMPLETION) {
					int position = commandLine.getSelectionEnd();
					StringBuffer buffer = new StringBuffer(commandLine.getText());
					buffer.insert(position, " ");
					commandLine.setText(buffer.toString());
					commandLine.setCaretPosition(position + 1);
					mode = Status.INSERT;
				} else {
					commandLine.replaceSelection("\t");
				}
			}
		}

		private class CompletionTask implements Runnable {
			private String completion;
			private int position;

			CompletionTask(String completion, int position) {
				this.completion = completion;
				this.position = position;
			}

			public void run() {
				StringBuffer buffer = new StringBuffer(commandLine.getText());
				buffer.insert(position, completion);
				commandLine.setText(buffer.toString());
				commandLine.setCaretPosition(position + completion.length());
				commandLine.moveCaretPosition(position);
				mode = Status.COMPLETION;
			}
		}

	}
}
