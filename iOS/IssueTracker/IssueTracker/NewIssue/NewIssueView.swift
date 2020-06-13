import UIKit

class NewIssueView: UIView {
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var bodyTextView: UITextView!

    var userInputData: (title: String, body: String) {
        (title: titleTextField.text ?? "", body: bodyTextView.text)
    }
}
