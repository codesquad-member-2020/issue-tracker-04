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
    case collapsed
}

class IssueListViewController: UIViewController {
    // MARK: - Property

    @IBOutlet weak var tableView: IssueListTableView!
    @IBOutlet weak var titleLabel: UILabel!

    private let defaultTitle = "Issue"

    let delegate = IssueListTableViewDelegate()
    private let issueListModelController = IssueListModelController()
    private var dataSource: IssueListDataSource = .init()

    var issueListState: IssueListState = .normal {
        didSet {
            updateIssueListState()
        }
    }

    // MARK: - View Cycle

    override func viewDidLoad() {
        super.viewDidLoad()

        let loader = IssueLoader()
        loader.loadList { result in
            if case .success(let issueList) = result {
                DispatchQueue.main.async {
                    self.issueListModelController.issueCollection = IssueCollection(elements: issueList.allData)
                }
            }
        }

        issueListModelController.addObserver(self)
        updateIssueListState()
        setupTableView()
    }

    override func viewWillAppear(_ animated: Bool) {
        super.viewWillAppear(animated)

        if let indexPath = tableView.indexPathForSelectedRow {
            tableView.deselectRow(at: indexPath, animated: true)
        }
    }

    // MARK: - Setup Views

    private func updateIssueListState() {
        let group = makeButtonGroup(issueListState)
        setupBarButtons(group: group)
        setupTableView()
        tableView.allowsMultipleSelectionDuringEditing = true
        tableView.issueStateDelegate = self
    }

    private func setupTableView() {
        let issueList = issueListModelController.issueCollection
        dataSource = IssueListDataSource(issueList)

        tableView.dataSource = dataSource
        tableView.delegate = delegate
    }

    // MARK: - Navigation

    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return issueListState == .normal
    }

    @IBSegueAction func showDetail(coder: NSCoder, sender: Any) -> IssueDetailViewController? {
        guard let indexPath = tableView.indexPathForSelectedRow else { return nil }

        let briefIssue = dataSource.issue(at: indexPath.row)
        let issue = issueListModelController.convertToDetailIssue(from: briefIssue)
        return IssueDetailViewController(coder: coder, issueModelController: IssueModelController(issue))
    }

    @IBSegueAction func showIssueForm(coder: NSCoder) -> IssueFormViewController? {
        IssueFormViewController(coder: coder, state: .new, delegate: self)
    }

    @IBAction func backFromDetail(unwindSegue: UIStoryboardSegue) {
        guard let detail = unwindSegue.source as? IssueDetailViewController else { return }

        let issue = issueListModelController.convertToBriefIssue(from: detail.issue)
        issueListModelController.updateIssue(with: issue)
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
        let storyboard = UIStoryboard(name: Identifier.Storyboard.main, bundle: nil)
        let viewController = storyboard.instantiateViewController(identifier: Identifier.ViewController.issueFilter) {
            IssueFilterViewController(coder: $0)
        }
        present(viewController, animated: true, completion: nil)
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

    // MARK: - Manage State

    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        tableView.setEditing(editing, animated: true)
    }

    private func setupBarButtons(group: BarButtonGroup) {
        navigationItem.leftBarButtonItem = group.left
        navigationItem.rightBarButtonItem = group.right

        toolbarItems = group.bottom
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

    private func makeButtonGroup(_ state: IssueListState) -> BarButtonGroup {
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

}

extension IssueListViewController: IssueStateDelegate {
    func stateDidChange(to state: IssueListState) {
        self.issueListState = state
    }
}

extension IssueListViewController: IssueFormViewControllerDelegate {
    func issueFormViewControllerDidCreate(_ partial: PartialIssue) {
        issueListModelController.addPartialIssue(partial)
    }
}

extension IssueListViewController: Observer {
    func ObservingObjectDidUpdate() {
        dataSource.updateList(issueListModelController.issueCollection)
        tableView.reloadData()
    }
}
