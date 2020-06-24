import UIKit

class AssigneeCellViewController: UIViewController {
    
    @IBOutlet weak var assigneesTableView: UITableView!
    override func viewDidLoad() {
        super.viewDidLoad()
        
        assigneesTableView.dataSource = self
        assigneesTableView.delegate = self
    }
    
}

extension AssigneeCellViewController: UITableViewDataSource{
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 5
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "AssigneeCell", for: indexPath)
        cell.textLabel?.text = "hi there"
        cell.imageView?.image = UIImage(systemName: "person.crop.circle.fill")
        cell.imageView?.cornerRadius = 20
        return cell
    }

}

extension AssigneeCellViewController: UITableViewDelegate{
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 40
    }

}
