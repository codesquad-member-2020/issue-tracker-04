import UIKit

class NewIssueViewController: UIViewController {
    typealias Texts = (title: String, body: String?)

    private(set) var issue: Issue?
    private let user: User = .init(id: 1, name: "Foo")
    private let issueID: ID = 1

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        super.shouldPerformSegue(withIdentifier: identifier, sender: sender)

        debugPrint("Segue identifier: \(identifier)")
        debugPrint(#file, #function)
        // TODO: 입력 텍스트 유효성 검사
        return true
    }

    // MARK: - IBActions

    @IBAction func cancelTouched(_ sender: UIButton) {
        dismiss(animated: true, completion: nil)
    }

    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        guard let identifier = segue.identifier, identifier == Identifier.Segue.save else { return }

        let texts: Texts = (title: "Test title", body: "body text test")
        save(texts: texts)
    }

    // MARK: - Business Logic?

    private func save(texts: Texts) {
        self.issue = Issue(id: 1, title: texts.title, body: texts.body, owner: user)
    }

}
