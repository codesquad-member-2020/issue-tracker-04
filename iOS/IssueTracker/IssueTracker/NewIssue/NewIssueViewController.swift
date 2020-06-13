import UIKit

class NewIssueViewController: UIViewController {
    typealias Texts = (title: String, body: String?)

    private(set) var issue: Issue?
    private let user: User = .init(id: 1, name: "Foo")
    private let issueID: ID = 1

    // MARK: View Life Cycle

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    // MARK: - Navigation

    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        super.shouldPerformSegue(withIdentifier: identifier, sender: sender)

        let view = self.view as! NewIssueView
        if view.userInputData.title.isEmpty {
            showMessage(for: .invalidNewIssueTitle)
            return false
        }

        return true
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let identifier = segue.identifier, identifier == Identifier.Segue.save else { return }

        let view = self.view as! NewIssueView
        save(texts: view.userInputData)
    }

    // MARK: - IBAction

    @IBAction func cancelTouched(_ sender: UIButton) {
        dismiss(animated: true, completion: nil)
    }

    // MARK: - Business Logic?

    private func save(texts: Texts) {
        debugPrint(texts)
        self.issue = Issue(id: 1, title: texts.title, body: texts.body, owner: user)
    }

}

// MARK: - Alert Message

extension NewIssueViewController {
    private func showMessage(for type: ValidateType) {
        let alert = makeAlert(for: type)
        present(alert, animated: false, completion: nil)
    }

    private func makeAlert(for type: ValidateType) -> UIAlertController {
        let alert = UIAlertController(title: nil, message: nil, preferredStyle: .alert)
        (alert.title, alert.message) = type.message

        let defaultAction = UIAlertAction(title: "OK", style: .default, handler: nil)
        alert.addAction(defaultAction)

        return alert
    }
}

enum ValidateType {
    case invalidNewIssueTitle

    var message: (title: String, message: String) {
        switch self {
        case .invalidNewIssueTitle:
            return (title: "이슈 작성", message: "제목을 입력해야 합니다.")
        }
    }
}
