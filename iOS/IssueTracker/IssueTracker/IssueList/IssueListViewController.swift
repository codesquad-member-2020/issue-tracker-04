import UIKit

class IssueListViewController: UIViewController {
    
    
    enum IssueListState {
        case normal
        case edit(isSelected: Bool)
    }
    
    // MARK: - Property

    @IBOutlet weak var tableView: UITableView!
    @IBOutlet weak var titleLabel: UILabel!
    
    private var dataSource: IssueListDataSource = .init()
    
    var cancelBarButton: UIBarButtonItem  {
        return UIBarButtonItem(barButtonSystemItem: .cancel, target: self, action: #selector(didCancelButtonPressed))
    }
    
    var editBarButton: UIBarButtonItem {
        return UIBarButtonItem(barButtonSystemItem: .edit, target: self, action: #selector(didEditButtonPressed))
    }
    
    var filterBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Filter", style: .plain, target: self, action: #selector(didFilterButtonPressed))
    }
    
    var selectAllBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Select All", style: .plain, target: self, action: #selector(didSelectAllButtonPressed))
    }
    
    var deselectAllBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Deselect All", style: .plain, target: self, action: #selector(didDeselectAllButtonPressed))
    }
    
    var closeBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Close Issue", style: .plain, target: self, action: #selector(didCloseButtonPressed))
    }
    
    // ViewController
    override var isEditing: Bool {
        didSet {
            // isEditing이 바뀌면 네비게이션바의 버튼과 툴바 버튼을 다시 세팅하기 위한 메소드를 호출한다.
            setupButtons()
        }
    }
    
    // MARK: - View Cycle

    override func viewDidLoad() {
        super.viewDidLoad()
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
                
        // normal 모드일 때 바 버튼을 세팅하는 메소드
        configureBarWhenNormal()
    }

    // MARK: - Tableview state

    private func setupButtons() {
        switch isEditing {
        case true:
            // Edit 모드에서 네비게이션바, 툴바 버튼 세팅하는 메소드
            configureBarWhenEdit()
            
        case false:
            // normal 모드에서 네비게이션바, 툴바 버튼 세팅하는 메소드
            configureBarWhenNormal()
        }
    }
    
    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        // 현재 화면의 edit 여부와  tableView의 edit을 isEditing을 같게 함. 동치.
        // viewController의 editing에 따라서 네비게이션바,툴바 버튼을 바꾸기 위해서.
        tableView.setEditing(editing, animated: true)
    }
    
    // MARK: - Normal -> Edit(Non)
    private func changeModeToEdit() {
        // TableView 다중 선택 (TableView row 왼쪽에 check mark를 활성화)
        tableView.allowsMultipleSelectionDuringEditing = true
        // 현재 화면을 edit 모드로 바꿈.
        isEditing = true
    }
    
    fileprivate func configureBarWhenEdit() {
        // 여기서 navigationItem은 ViewController에 embed한 네비게이션바
        // Edit모드일 때 모든 행위를 취소하고(아무것도 저장하지 않는다) 이전화면을 돌아가는 네비게이션바 오른쪽 버튼을 Cancel 버튼을 표시한다.
        navigationItem.rightBarButtonItem = cancelBarButton
        // Edit모드일 때 선택한 이슈가 없을 경우 네비게이션바 왼쪽 버튼을 SelectAll 버튼을 표시한다.
        navigationItem.leftBarButtonItem = selectAllBarButton
    }
    
    // MARK: - Edit(Non) -> Normal
    private func configureBarWhenNormal() {
        // 화면이 normal모드일 때 closeIssue 버튼을 숨기기 위해 툴바를 숨긴다.
        // 화면에 embed한 네비게이션컨트롤러에 내장되어있는 툴바를 숨긴다
        navigationController?.setToolbarHidden(true, animated: false)
        // normal 모드에서 edit 모드로 갈 수 있도록 네비게이션바의 오른쪽 버튼을 Edit 버튼으로 표시한다.
        navigationItem.rightBarButtonItem = editBarButton
        // normal 모드에서 filter 화면으로 전환될 수 있도록 네비게이션바의 왼쪽 버튼을 Filter 버튼으로 표시한다.
        navigationItem.leftBarButtonItem = filterBarButton
    }
    
    private func changeModeToNormal() {
        // 화면의 edit 모드를 종료. normal 모드로 변경
        isEditing = false
    }
    
    // MARK: - Edit(Non) -> Edit(Sel)
    private func updateBarButtonWhenSelected() {
        // 선택한 이슈가 하나라도 있는 경우에 네비게이션바 왼쪽 버튼을 DeselectAll 버튼으로 변경
        navigationItem.leftBarButtonItem = deselectAllBarButton
        // 선택한 이슈가 있을경우 CloseIssue를 테이블뷰 하단에 툴바에 표시
        navigationController?.setToolbarHidden(false, animated: false)
        toolbarItems = [closeBarButton]
    }
    // MARK: - Edit(Sel) -> Edit(Non)
    private func updateBarButtonWhenNonSelected() {
        // edit 모드일 때 선택한 이슈가 없을 경우 네비게이션 왼쪽 버튼을 SelectAll 버튼으로 표시한다.
        navigationItem.leftBarButtonItem = selectAllBarButton
        // edit 모드일 때 선택한 이슈가 없을 경우에는 CloseIssue 버튼을 표시하지 않는다.
        navigationController?.setToolbarHidden(true, animated: false)
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
    @objc private func didFilterButtonPressed() {
        
    }
    
    fileprivate func selectAllRowsWhenEdit() {
        // 이슈 목록에 있는 모든 이슈의 check mark를 선택 표시한다.
        for row in 0..<tableView.numberOfRows(inSection: 0) {
            tableView.selectRow(at: IndexPath(row: row, section: 0), animated: true, scrollPosition: .none)
        }
        // 선택된 이슈의 개수를 title에 표시한다.
        titleLabel.text = titleWhenEdit()
    }
    
    @objc private func didSelectAllButtonPressed(_ sender: UIBarButtonItem) {
        // 현재 이슈 목록의 모든 이슈의 check mark를 선택으로 표시한다
        selectAllRowsWhenEdit()
        // 선택한 이슈가 있을 때 CloseIssue 버튼을 표시한다.
        updateBarButtonWhenSelected()
    }
    
    fileprivate func deselectAllRowsWhenSelected() {
        for row in 0..<tableView.numberOfRows(inSection: 0) {
            tableView.deselectRow(at: IndexPath(row: row, section: 0), animated: true)
        }
        // 선택된 이슈의 개수를 title에 표시한다.
        titleLabel.text = titleWhenEdit()
    }
    
    @objc private func didDeselectAllButtonPressed() {
        // 선택한 이슈가 하나 이상일 경우 선택한 모든 이슈의 check mark를 선택 해제한다.
        deselectAllRowsWhenSelected()
        // 선택한 이슈가 하나도 없을 때 네비게이션바 왼쪽 버튼을 Select All 버튼으로 표시하고 Close Issue 버튼을 표시하지 않는다.
        updateBarButtonWhenNonSelected()
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

    @IBSegueAction func showDetail(coder: NSCoder, sender: IssueCell) -> IssueDetailViewController? {
        guard let indexPath = tableView.indexPathForSelectedRow else { return nil }

        let issue = dataSource.issue(at: indexPath.row)
        return IssueDetailViewController(coder: coder, issue: issue)
    }

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
        self.navigationItem.leftBarButtonItem = deselectAllBarButton
        if isSelectedRows() {
            titleLabel.text = titleWhenEdit()
            showCloseButtonWhenSelected()
        }
    }
    
    private func titleWhenEdit() -> String {
        "\(tableView.indexPathsForSelectedRows?.count ?? 0)개 선택"
    }
    
    private func showCloseButtonWhenSelected() {
        // Close Issue 버튼을 표시한다.
        toolbarItems = [closeBarButton]
        navigationController?.setToolbarHidden(false, animated: false)
    }
    
    func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
        // 선택된 이슈가 하나도 없을 때
        if !isSelectedRows() {
            // 네비게이션바 왼쪽에 Select All 버튼을 표시하고 Close Issue 버튼을 표시하지 않는다
            barButtonWhenNonSelected()
        }
        // 선택된 이슈의 개수를 title에 표시한다.
        titleLabel.text = titleWhenEdit()
    }
    
    private func barButtonWhenNonSelected() {
        // 아무 이슈도 선택되지 않았을 때 네비게이션바의 왼쪽 버튼을 Select All 버튼으로 표시한다.
        navigationItem.leftBarButtonItem = selectAllBarButton
        // 아무 이슈도 선택되지 않았을 때 Close Issue 버튼을 표시하지 않는다.
        navigationController?.setToolbarHidden(true, animated: false)
    }
    
    func isSelectedRows() -> Bool {
        // 선택된 이슈가 하나 이상일 때 true를 반환한다.
        tableView.indexPathsForSelectedRows != nil
    }
    
}
