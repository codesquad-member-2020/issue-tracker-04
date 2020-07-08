import UIKit

class LabelFormView: UIView {
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var descriptionTextField: UITextField!
    @IBOutlet weak var colorTextField: UITextField!

    override func awakeFromNib() {
        super.awakeFromNib()

        titleTextField.placeholder = "Title"
    }

    func clearAllFields() {
        let textFields: [UITextField?] = [titleTextField, descriptionTextField, colorTextField]
        textFields.forEach { $0?.text = "" }
    }
}
