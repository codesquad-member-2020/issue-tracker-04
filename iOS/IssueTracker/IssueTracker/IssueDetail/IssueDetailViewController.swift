import UIKit

class IssueDetailViewController: UIViewController {
    @IBOutlet weak var issueDetailView: IssueDetailView!

    let issueModelController: IssueModelController
    private var issue: Issue { issueModelController.issue }

    init?(coder: NSCoder, issueModelController: IssueModelController) {
        self.issueModelController = issueModelController
        super.init(coder: coder)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        let configurator = IssueDetailViewConfigurator()
        configurator.configure(issueDetailView, with: issue)
    }

    // MARK: - Navigation

    @IBSegueAction
    private func showEditIssue(coder: NSCoder) -> IssueFormViewController? {
        return IssueFormViewController(coder: coder, issue: issue)
    }

}

class IssueDetailViewConfigurator {
    func configure(_ view: IssueDetailView, with issue: Issue) {
        view.titleLabel.text = issue.title
        view.bodyView.text = issue.body
        view.authorLabel.text = String(describing: issue.owner)
    }
}
