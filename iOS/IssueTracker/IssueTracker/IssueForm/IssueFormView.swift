import UIKit

class IssueFormView: UIView {
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var bodyTextView: UITextView!

    var userInput: PartialIssue {
        .init(title: titleTextField.text ?? "", body: bodyTextView.text)
    }

    override func awakeFromNib() {
        super.awakeFromNib()
        titleTextField.delegate = self
    }

}

extension IssueFormView: UITextFieldDelegate {
    func textFieldShouldReturn(_ textField: UITextField) -> Bool {
        if textField == titleTextField {
            bodyTextView.becomeFirstResponder()
        }

        return true
    }
}
