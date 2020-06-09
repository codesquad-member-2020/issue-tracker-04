import UIKit

class IssueListViewController: UIViewController {
    @IBOutlet weak var tableView: UITableView!

    override func viewDidLoad() {
        super.viewDidLoad()

        tableView.dataSource = self
    }

}

extension IssueListViewController: UITableViewDataSource {
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 10
    }

    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: "IssueCell") else { return UITableViewCell() }

        cell.textLabel?.text = "이슈 목록 뷰컨트롤러 만들기"
        cell.detailTextLabel?.text = "테이블 뷰 데이터소스 \n트래커 커멘트 \n스와이프할때 delete"
        return cell
    }
}
