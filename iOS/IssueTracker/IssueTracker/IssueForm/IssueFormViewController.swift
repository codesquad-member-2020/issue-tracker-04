import UIKit

class IssueFormViewController: UIViewController {
    typealias Texts = (title: String, body: String?)

    @IBOutlet weak var formView: IssueFormView!

    private(set) var issue: Issue?
    private let user: User = .init(id: 1, name: "Foo")

    init?(coder: NSCoder, issue: Issue?) {
        self.issue = issue
        super.init(coder: coder)
    }

    required init?(coder: NSCoder) {
        super.init(coder: coder)
    }

    // MARK: View Life Cycle

    override func viewDidLoad() {
        super.viewDidLoad()

        configureFormView()
    }

    private func configureFormView() {
        formView.titleTextField.text = issue?.title
        formView.bodyTextView.text = issue?.body
    }

    // MARK: - Navigation

    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        super.shouldPerformSegue(withIdentifier: identifier, sender: sender)

        if formView.userInput.title.isEmpty {
            showMessage(for: .invalidNewIssueTitle)
            return false
        }

        return true
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let identifier = segue.identifier, identifier == Identifier.Segue.save else { return }

        save(texts: formView.userInput)
    }

    // MARK: - IBAction

    @IBAction func cancelTouched(_ sender: UIButton) {
        dismiss(animated: true, completion: nil)
    }

    // MARK: - Business Logic?

    private func save(texts: Texts) {
        self.issue = Issue(id: 1, title: texts.title, body: texts.body, owner: user)
    }

}

// MARK: - Alert Message

extension IssueFormViewController {
    private func showMessage(for type: ValidateType) {
        let alert = makeAlert(for: type)
        present(alert, animated: false, completion: nil)
    }

    private func makeAlert(for type: ValidateType) -> UIAlertController {
        let alert = UIAlertController(title: nil, message: nil, preferredStyle: .alert)
        (alert.title, alert.message) = type.content

        let defaultAction = UIAlertAction(title: "OK", style: .default, handler: nil)
        alert.addAction(defaultAction)

        return alert
    }
}

enum ValidateType {
    case invalidNewIssueTitle

    typealias Content = (title: String, message: String)

    var content: Content {
        switch self {
        case .invalidNewIssueTitle:
            return Content(title: "이슈 작성", message: "제목을 입력해야 합니다.")
        }
    }
}
