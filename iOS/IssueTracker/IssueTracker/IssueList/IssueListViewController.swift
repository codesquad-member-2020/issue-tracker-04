import UIKit

enum IssueListState {
    case normal
    case edit(isSelected: Bool)
}

class IssueListViewController: UIViewController {
    var issueListState: IssueListState = .normal {
        didSet {
            let group = makeButtonGroup(issueListState)
            setupBarButtons(group: group)
        }
    }

    // MARK: - Property

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var titleLabel: UILabel!
    
    private var dataSource: IssueListDataSource = .init()

    func makeButtonGroup(_ state: IssueListState) -> BarButtonGroup {
        var group: BarButtonGroup
        switch state {
        case .normal:
            group.left = UIBarButtonItem(title: "Filter", style: .plain, target: self, action: #selector(didFilterButtonPressed))
            group.right = UIBarButtonItem(title: "Edit", style: .plain, target: self, action: #selector(didEditButtonPressed))
            group.bottom = []
        case .edit(let isSelected):
            navigationController?.setToolbarHidden(false, animated: true)
            if isSelected {
                group.left = UIBarButtonItem(title: "Deselect All", style: .plain, target: self, action: #selector(didDeselectAllButtonPressed))
                group.right = UIBarButtonItem(title: "Cancel", style: .plain, target: self, action: #selector(didCancelButtonPressed))
                group.bottom = [UIBarButtonItem(title: "Close Issue", style: .plain, target: self, action: #selector(didCloseButtonPressed))]
            } else {
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
        // 화면이 처음 그려질 때 TableView를 세팅하기 위한 메소드 호출
        setupTableView()
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        // normal 모드일 경우에는 perfom segue가 true이고 셀을 터치하면 상세 화면이 열린다.
        // edit 모드(isEditing = true)일 경우에는 false가 되어서 이슈 상세화면이 열리지 않는다.
        return !tableView.isEditing
    }
    
    private func setupTableView() {
        // fake 데이터
        let user = User(id: 1, name: "모오오오스")
        let issueList: IssueCollection = [Issue(id: 1, title: "title1", body: nil, owner: user),Issue(id: 2, title: "title2", body: "Something", owner: user),Issue(id: 3, title: "title3", body: "Special", owner: user)]
        
        // TableView DataSource / Delegate 설정
        dataSource = IssueListDataSource(issueList)
        tableView.dataSource = dataSource
        tableView.delegate = self
    }

    // MARK: - Normal -> Edit(Non)
    private func changeModeToEdit() {
        // TableView 다중 선택 (TableView row 왼쪽에 check mark를 활성화)
        tableView.allowsMultipleSelectionDuringEditing = true
        // 현재 화면을 edit 모드로 바꿈.
        isEditing = true
        issueListState = .edit(isSelected: false)
    }

    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        // 현재 화면의 edit 여부와  tableView의 edit을 isEditing을 같게 함. 동치.
        // viewController의 editing에 따라서 네비게이션바,툴바 버튼을 바꾸기 위해서.
        tableView.setEditing(editing, animated: true)
    }
    
    private func changeModeToNormal() {
        // 화면의 edit 모드를 종료. normal 모드로 변경
        isEditing = false
        issueListState = .normal
    }
    
    // MARK: - Edit(Sel) -> Normal

    fileprivate func toggleIsEditing() {
        // 화면의 Edit 모드를 바꾼다.
        isEditing = !isEditing
    }
    
    // MARK: - Selector Method
    // 네비게이션
    @objc private func didCancelButtonPressed() {
        changeModeToNormal()
    }
    
    @objc private func didEditButtonPressed() {
        changeModeToEdit()
    }
    
    // TODO: present view to select filtering options.
    @objc func didFilterButtonPressed() {
        
    }
    
    fileprivate func selectAllRowsWhenEdit() {
        // 이슈 목록에 있는 모든 이슈의 check mark를 선택 표시한다.
        for row in 0..<tableView.numberOfRows(inSection: 0) {
            tableView.selectRow(at: IndexPath(row: row, section: 0), animated: true, scrollPosition: .none)
        }
        // 선택된 이슈의 개수를 title에 표시한다.
        titleLabel.text = titleWhenEdit()
    }
    
    @objc private func didSelectAllButtonPressed() {
        // 현재 이슈 목록의 모든 이슈의 check mark를 선택으로 표시한다
        selectAllRowsWhenEdit()
        issueListState = .edit(isSelected: true)
    }
    
    fileprivate func deselectAllRowsWhenSelected() {
        for row in 0..<tableView.numberOfRows(inSection: 0) {
            tableView.deselectRow(at: IndexPath(row: row, section: 0), animated: true)
        }
        // 선택된 이슈의 개수를 title에 표시한다.
        titleLabel.text = titleWhenEdit()
        issueListState = .edit(isSelected: false)
    }
    
    @objc private func didDeselectAllButtonPressed() {
        // 선택한 이슈가 하나 이상일 경우 선택한 모든 이슈의 check mark를 선택 해제한다.
        deselectAllRowsWhenSelected()

    }
    
    fileprivate func closeIssuesWhenSelected() {
        // 이슈 목록에서 선택한 이슈의 상태롤 close로 바꾼다.
        tableView.indexPathsForSelectedRows?.forEach{
            dataSource.closeIssue(at: $0.row)
        }
    }
    
    @objc private func didCloseButtonPressed() {
        // 선택한 이슈의 상태를 close로 변경한다
        closeIssuesWhenSelected()
        // 화면을 normal 모드로 변경한다
        toggleIsEditing()
    }
  
    // MARK: - Navigation

    @IBAction func newIssueDidCreated(_ segue: UIStoryboardSegue) {
        guard let viewController = segue.source as? IssueFormViewController,
            let issue = viewController.issue else { return }

        dataSource.add(issue: issue)
        tableView.reloadData()
    }

//    @IBSegueAction func showDetail(coder: NSCoder, sender: IssueCell) -> IssueDetailViewController? {
//        guard let indexPath = tableView.indexPathForSelectedRow else { return nil }
//
//        let issue = dataSource.issue(at: indexPath.row)
//        return IssueDetailViewController(coder: coder, issue: issue)
//    }

}

extension IssueListViewController: UITableViewDelegate {
    func tableView(_ tableView: UITableView, trailingSwipeActionsConfigurationForRowAt indexPath: IndexPath) -> UISwipeActionsConfiguration? {
        let close = UIContextualAction(style: .normal, title: "Close") { action, view, completion in
            guard let dataSource = tableView.dataSource as? IssueListDataSource else { return }
            dataSource.closeIssue(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
            debugPrint("Close")
            completion(true)
        }
        
        let delete = UIContextualAction(style: .destructive, title: "Delete") { action, view, completion in
            guard let dataSource = tableView.dataSource as? IssueListDataSource else { return }
            dataSource.removeIssue(at: indexPath.row)
            tableView.deleteRows(at: [indexPath], with: .automatic)
            debugPrint("Delete")
            completion(true)
        }

        close.backgroundColor = .systemGreen
        close.image = UIImage(systemName: SystemImageName.cellClose)
        delete.image = UIImage(systemName: SystemImageName.cellDelete)

        let swipeAction = UISwipeActionsConfiguration(actions: [close, delete])

        return swipeAction
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
//        self.navigationItem.leftBarButtonItem = deselectAllBarButton
        if isSelectedRows() {
            titleLabel.text = titleWhenEdit()
            issueListState = .edit(isSelected: true)
        }
    }
    
    private func titleWhenEdit() -> String {
        "\(tableView.indexPathsForSelectedRows?.count ?? 0)개 선택"
    }
    
    func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
        // 선택된 이슈의 개수를 title에 표시한다.
        titleLabel.text = titleWhenEdit()
        if !isSelectedRows() {
            issueListState = .edit(isSelected: false)
        }
    }
    
    func isSelectedRows() -> Bool {
        // 선택된 이슈가 하나 이상일 때 true를 반환한다.
        tableView.indexPathsForSelectedRows != nil
    }
    
}
