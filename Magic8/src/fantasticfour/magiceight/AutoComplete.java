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
		private JTextField textField;
		private final List<String> keywords;
		private Status mode = Status.INSERT;

		public Autocomplete(JTextField textField, List<String> keywords) {
			this.textField = textField;
			this.keywords = keywords;
			Collections.sort(keywords);
		}

		@Override
		public void changedUpdate(DocumentEvent ev) { 
			
		}

		@Override
		public void removeUpdate(DocumentEvent ev) {
			
		}

		@Override
		public void insertUpdate(DocumentEvent ev) {
			if (ev.getLength() != 1)
				return;

			int pos = ev.getOffset();
			String content = null;
			try {
				content = textField.getText(0, pos + 1);
			} catch (BadLocationException e) {
				e.printStackTrace();
			}

			// Find where the word starts
			int w;
			for (w = pos; w >= 0; w--) {
				if (!Character.isLetter((content.charAt(w)))) {
					break;
				}
			}

			// Too few chars
			if (pos - w < 1)
				return;

			String prefix = content.substring(w + 1).toLowerCase();
			int n = Collections.binarySearch(keywords, prefix);
			if (n < 0 && -n <= keywords.size()) {
				String match = keywords.get(-n - 1);
				if (match.startsWith(prefix)) {

					String completion = match.substring(pos - w);
					// We cannot modify Document from within notification,
					// so we submit a task that does the change later
					SwingUtilities.invokeLater(new CompletionTask(completion, pos + 1));
				}
			} else {
				// Nothing found
				mode = Status.INSERT;
			}
		}

		public class CommitAction extends AbstractAction {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent ev) {
				if (mode == Status.COMPLETION) {
					int pos = textField.getSelectionEnd();
					StringBuffer sb = new StringBuffer(textField.getText());
					sb.insert(pos, " ");
					textField.setText(sb.toString());
					textField.setCaretPosition(pos + 1);
					mode = Status.INSERT;
				} else {
					textField.replaceSelection("\t");
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
				StringBuffer sb = new StringBuffer(textField.getText());
				sb.insert(position, completion);
				textField.setText(sb.toString());
				textField.setCaretPosition(position + completion.length());
				textField.moveCaretPosition(position);
				mode = Status.COMPLETION;
			}
		}

	}
}
