import UIKit

class IssueDetailViewController: UIViewController {
    @IBOutlet weak var issueDetailView: IssueDetailView!

    private let issueModelController: IssueModelController

    init?(coder: NSCoder, issueModelController: IssueModelController) {
        self.issueModelController = issueModelController
        super.init(coder: coder)
    }

    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        issueModelController.add(observer: self)
        configureView()
    }

    private func configureView() {
        let configurator = IssueDetailViewConfigurator()
        configurator.configure(issueDetailView, with: issueModelController.issue)
    }

    // MARK: - Navigation

    @IBSegueAction private func showEditIssue(coder: NSCoder) -> IssueFormViewController? {
        IssueFormViewController(coder: coder, state: .edit(issue: issueModelController.issue), delegate: self)
    }

}

class IssueDetailViewConfigurator {
    func configure(_ view: IssueDetailView, with issue: Issue) {
        view.titleLabel.text = issue.title
        view.bodyView.text = issue.body
        view.authorLabel.text = String(describing: issue.owner)
    }
}

extension IssueDetailViewController: IssueFormViewControllerDelegate {
    func issueFormViewControllerDidEdit(issue: Issue) {
        issueModelController.update(issue: issue)
    }
}

extension IssueDetailViewController: IssueModelControllerObserver {
    func issueModelControllerDidUpdate(_ controller: IssueModelController) {
        configureView()
    }
}
