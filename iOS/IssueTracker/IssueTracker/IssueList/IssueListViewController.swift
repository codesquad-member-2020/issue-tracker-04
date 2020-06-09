import UIKit

class IssueListViewController: UIViewController {
    
    private let dataSource = IssueListDataSource()
    @IBOutlet weak var tableView: UITableView!

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.dataSource = dataSource
    }

}

