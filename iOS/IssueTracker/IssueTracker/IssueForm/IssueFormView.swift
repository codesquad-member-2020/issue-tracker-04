import UIKit

class IssueFormView: UIView {
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var bodyTextView: UITextView!

    var userInput: (title: String, body: String) {
        (title: titleTextField.text ?? "", body: bodyTextView.text)
    }
}
