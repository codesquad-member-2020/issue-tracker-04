import UIKit

class AssigneeCellViewController: UIViewController {
    
    @IBOutlet weak var assigneesTableView: UITableView!
    private let heightForRowAt = CGFloat(40)
    var assignees: [User]?
    override func viewDidLoad() {
        super.viewDidLoad()
        
        assigneesTableView.dataSource = self
        assigneesTableView.delegate = self
    }
    
}

extension AssigneeCellViewController: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return assignees?.count ?? 5
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "AssigneeCell", for: indexPath)
        cell.textLabel?.text = assignees?[indexPath.row].name
        cell.textLabel?.textColor = .black
        cell.imageView?.image = #imageLiteral(resourceName: "github.logo.fill")
        cell.imageView?.cornerRadius = 20
        return cell
    }

}

extension AssigneeCellViewController: UITableViewDelegate{
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return heightForRowAt
    }

}
