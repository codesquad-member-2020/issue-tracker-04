import UIKit

protocol IssueFormViewControllerDelegate: class {
    func issueFormViewControllerDidCreate(_ partial: PartialIssue)
    func issueFormViewControllerDidEdit(_ issue: Issue)
}

extension IssueFormViewControllerDelegate {
    func issueFormViewControllerDidCreate(_ partial: PartialIssue) { }
    func issueFormViewControllerDidEdit(_ issue: Issue) { }
}

class IssueFormViewController: UIViewController {
    enum State {
        case new
        case edit(issue: Issue)
    }

    @IBOutlet weak var formView: IssueFormView!

    private let user: User = .init(id: 1, name: "Foo")
    weak var delegate: IssueFormViewControllerDelegate?
    let state: State

    init?(coder: NSCoder, state: State, delegate: IssueFormViewControllerDelegate? = nil) {
        self.state = state
        self.delegate = delegate
        super.init(coder: coder)
    }

    required init?(coder: NSCoder) {
        self.state = .new
        super.init(coder: coder)
    }

    // MARK: View Life Cycle

    override func viewDidLoad() {
        super.viewDidLoad()

        configureFormView()
    }

    private func configureFormView() {
        guard case .edit(let issue) = state else { return }

        formView.titleTextField.text = issue.title
        formView.bodyTextView.text = issue.body
    }

    // MARK: - IBAction

    @IBAction func didTouchSave(_ sender: UIButton) {
        guard !formView.userInput.title.isEmpty else {
            showMessage(for: .invalidNewIssueTitle)
            return
        }

        save(texts: formView.userInput)
        dismiss(animated: true, completion: nil)
    }

    @IBAction func didTouchCancel(_ sender: UIButton) {
        dismiss(animated: true, completion: nil)
    }

    // MARK: - Business Logic?

    private func save(texts: PartialIssue) {
        switch state {
        case .new:
            delegate?.issueFormViewControllerDidCreate(texts)
        case .edit(let oldIssue):
            var issue = oldIssue
            (issue.title, issue.body) = (texts.title, texts.body)
            delegate?.issueFormViewControllerDidEdit(issue)
        }
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
}
