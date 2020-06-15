import UIKit

class IssueDetailViewController: UIViewController {
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var bodyView: UITextView!
    @IBOutlet weak var ownerLabel: UILabel!

    var issue: Issue

    init?(coder: NSCoder, issue: Issue) {
        self.issue = issue
        super.init(coder: coder)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        titleLabel.text = issue.title
        bodyView.text = issue.body
        ownerLabel.text = String(describing: issue.owner)
    }

    // MARK: - Navigation

    @IBSegueAction
    private func showEditIssue(coder: NSCoder) -> IssueFormViewController? {
        return IssueFormViewController(coder: coder, issue: issue)
    }

}
