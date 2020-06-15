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
        navigationController?.setToolbarHidden(true, animated: false)
    }
    
    var deselectAllBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Deselect All", style: .plain, target: self, action: #selector(didDeselectAllButtonPressed))
    }
    
    var closeBarButton: UIBarButtonItem {
        return UIBarButtonItem(title: "Close Issue", style: .plain, target: self, action: #selector(didCloseButtonPressed))
    }
    
    override var isEditing: Bool {
        didSet {
            setupButtons()
        }
    }
    
    // MARK: - View Cycle

    override func viewDidLoad() {
        super.viewDidLoad()
        
        setupTableView()
    }
    
    override func shouldPerformSegue(withIdentifier identifier: String, sender: Any?) -> Bool {
        return !tableView.isEditing
    }
    
    private func setupTableView() {
        // fake 데이터
        let user = User(id: 1, name: "모오오오스")
        let issueList: IssueCollection = [Issue(id: 1, title: "title1", body: nil, owner: user),Issue(id: 2, title: "title2", body: "Something", owner: user),Issue(id: 3, title: "title3", body: "Special", owner: user)]
        
        // 테이블 뷰 데이터 소스/델리게이트 설정
        dataSource = IssueListDataSource(issueList)
        tableView.dataSource = dataSource
        tableView.delegate = self
        
        // 테이블 뷰 다중 선택
        tableView.allowsMultipleSelectionDuringEditing = true
        
        configureBarWhenNormal()
    }

    // MARK: - tableview state

    private func setupButtons() {
        switch isEditing {
        case true:
            configureBarWhenEdit()
            
        case false:
            configureBarWhenNormal()
        }
    }
    
    override func setEditing(_ editing: Bool, animated: Bool) {
        super.setEditing(editing, animated: animated)
        tableView.setEditing(editing, animated: true)
    }
    
    // MARK: - Normal -> Edit(Non)
    private func changeModeToEdit() {
        tableView.allowsMultipleSelectionDuringEditing = true
        isEditing = true
    }
    
    fileprivate func configureBarWhenEdit() {
        navigationItem.rightBarButtonItem = cancelBarButton
        navigationItem.leftBarButtonItem = selectAllBarButton
    }
    
    // MARK: - Edit(Non) -> Normal
    private func configureBarWhenNormal() {
        navigationController?.setToolbarHidden(true, animated: false)
        navigationItem.rightBarButtonItem = editBarButton
        navigationItem.leftBarButtonItem = filterBarButton
    }
    
    private func changeModeToNormal() {
        isEditing = false
    }
    
    // MARK: - Edit(Non) -> Edit(Sel)
    private func updateBarButtonWhenSelected() {
        navigationItem.leftBarButtonItem = deselectAllBarButton
        navigationController?.setToolbarHidden(false, animated: false)
        toolbarItems = [closeBarButton]
    }
    // MARK: - Edit(Sel) -> Edit(Non)
    private func updateBarButtonWhenNonSelected() {
        navigationItem.leftBarButtonItem = selectAllBarButton
        navigationController?.setToolbarHidden(true, animated: false)
    }
    
    // MARK: - Edit(Sel) -> Normal


    fileprivate func toggleIsEditing() {
        isEditing = !isEditing
    }
    
    // MARK: - Selector Method
    
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
        for row in 0..<tableView.numberOfRows(inSection: 0) {
            tableView.selectRow(at: IndexPath(row: row, section: 0), animated: true, scrollPosition: .none)
        }
        titleLabel.text = titleWhenEdit()
    }
    
    @objc private func didSelectAllButtonPressed(_ sender: UIBarButtonItem) {
        selectAllRowsWhenEdit()
        updateBarButtonWhenSelected()
    }
    
    
    fileprivate func deselectAllRowsWhenSelected() {
        for row in 0..<tableView.numberOfRows(inSection: 0) {
            tableView.deselectRow(at: IndexPath(row: row, section: 0), animated: true)
        }
    }
    
    @objc private func didDeselectAllButtonPressed() {
        deselectAllRowsWhenSelected()
        updateBarButtonWhenNonSelected()
    }
    
    
    
    fileprivate func closeIssuesWhenSelected() {
        tableView.indexPathsForSelectedRows?.forEach{
            dataSource.closeIssue(at: $0.row)
        }
    }
    
    @objc private func didCloseButtonPressed() {
        closeIssuesWhenSelected()
        
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
        toolbarItems = [closeBarButton]
        navigationController?.setToolbarHidden(false, animated: false)
    }
    
    func tableView(_ tableView: UITableView, didDeselectRowAt indexPath: IndexPath) {
        if !isSelectedRows() {
            
            barButtonWhenNonSelected()
        }
        titleLabel.text = titleWhenEdit()
    }
    
    private func barButtonWhenNonSelected() {
        navigationItem.leftBarButtonItem = selectAllBarButton
        navigationController?.setToolbarHidden(true, animated: false)
    }
    
    func isSelectedRows() -> Bool {
        tableView.indexPathsForSelectedRows != nil
    }
    
}
