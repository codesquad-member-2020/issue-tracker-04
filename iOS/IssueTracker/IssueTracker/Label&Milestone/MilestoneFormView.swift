import UIKit

class MilestoneFormView: UIView {
    @IBOutlet weak var titleTextField: UITextField!
    @IBOutlet weak var dueDateTextField: UITextField!
    @IBOutlet weak var descriptionTextField: UITextField!

    func clearAllFields() {
        let textFields: [UITextField?] = [titleTextField, dueDateTextField, descriptionTextField]
        textFields.forEach { $0?.text = "" }
    }
}
