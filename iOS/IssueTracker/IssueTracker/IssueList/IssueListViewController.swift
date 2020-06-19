import UIKit

enum IssueListState: Equatable {
    case normal
    case edit(select: Select)

    enum Select {
        case some, none
    }
}

protocol IssueStateDelegate: class {
    func stateDidChange(to state: IssueListState)
}

enum IssueInfoState {
    case expanded
    case collpased
}

class IssueListViewController: UIViewController {

    // MARK: - Property

    @IBOutlet weak var tableView: IssueListTableView!
    @IBOutlet weak var titleLabel: UILabel!
    let delegate = IssueListTableViewDelegate()
    private var dataSource: IssueListDataSource = .init()
    private let defaultTitle = "Issue"
    var issueListState: IssueListState = .normal {
        didSet {
            let group = makeButtonGroup(issueListState)
            setupBarButtons(group: group)
        }
    }

    func makeButtonGroup(_ state: IssueListState) -> BarButtonGroup {
        var group: BarButtonGroup
        switch state {
        case .normal:
            group.left = UIBarButtonItem(title: "Filter", style: .plain, target: self, action: #selector(didFilterButtonPressed))
            group.right = UIBarButtonItem(title: "Edit", style: .plain, target: self, action: #selector(didEditButtonPressed))
            group.bottom = []
        case .edit(let select):
            navigationController?.setToolbarHidden(false, animated: true)
            switch select {
            case .some:
                group.left = UIBarButtonItem(title: "Deselect All", style: .plain, target: self, action: #selector(didDeselectAllButtonPressed))
                group.right = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(didCancelButtonPressed))
                group.bottom = [UIBarButtonItem(title: "Close Issue", style: .plain, target: self, action: #selector(didCloseButtonPressed))]
            case .none:
                group.left = UIBarButtonItem(title: "Select All", style: .plain, target: self, action: #selector(didSelectAllButtonPressed))
                group.right = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(didCancelButtonPressed))
                group.bottom = []
            }
        }

        return group
    }

    func setupBarButtons(group: BarButtonGroup) {
        navigationItem.leftBarButtonItem = group.left
        navigationItem.rightBarButtonItem = group.right

        toolbarItems = group.bottom

    }

    // MARK: - View Cycle

    override func viewDidLoad() {
        super.viewDidLoad()

        let group = makeButtonGroup(issueListState)
        setupBarButtons(group: group)
        setupTableView()
        tableView.allowsMultipleSelectionDuringEditing = true
        tableView.issueStateDelegate = self
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return issueListState == .normal
    }
    
    private func setupTableView() {
        let user = User(id: 1, name: "모오오오스")
        let issueList: IssueCollection = [Issue(id: 1, title: "title1", body: nil, owner: user),Issue(id: 2, title: "title2", body: "Something", owner: user),Issue(id: 3, title: "title3", body: "Special", owner: user)]
        
        dataSource = IssueListDataSource(issueList)
        tableView.dataSource = dataSource
        tableView.delegate = delegate
    }

    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        tableView.setEditing(editing, animated: true)
    }

    func changeState(to state: IssueListState) {
        switch state {
        case .normal:
            isEditing = false
            issueListState = .normal
        case .edit(select: _):
            isEditing = true
            issueListState = .edit(select: .none)
        }
    }
    
    // MARK: - Selector Method
    @objc private func didCancelButtonPressed() {
        changeState(to: .normal)
        titleLabel.text = defaultTitle
    }
    
    @objc private func didEditButtonPressed() {
        changeState(to: .edit(select: .none))
    }
    
    // TODO: present view to select filtering options.
    @objc private func didFilterButtonPressed() {
        
    }

    @objc private func didSelectAllButtonPressed() {
        tableView.selectAllRowsWhenEdit()
        issueListState = .edit(select: .some)
    }

    @objc private func didDeselectAllButtonPressed() {
        tableView.deselectAllRowsWhenSelected()

    }

    @objc private func didCloseButtonPressed() {
        tableView.closeIssuesWhenSelected()
        changeState(to: .normal)
    }
    
    // MARK: - Navigation

    @IBAction func newIssueDidCreated(_ segue: UIStoryboardSegue) {
        guard let viewController = segue.source as? IssueFormViewController,
            let issue = viewController.issue else { return }

        dataSource.add(issue: issue)
        tableView.reloadData()
    }

    @IBSegueAction func showDetail(coder: NSCoder, sender: IssueCell) -> IssueDetailViewController? {
        guard let indexPath = tableView.indexPathForSelectedRow else { return nil }

        let issue = dataSource.issue(at: indexPath.row)
        return IssueDetailViewController(coder: coder, issue: issue)
    }

}

extension IssueListViewController: IssueStateDelegate {
    func stateDidChange(to state: IssueListState) {
        self.issueListState = state
    }
    
    
}
