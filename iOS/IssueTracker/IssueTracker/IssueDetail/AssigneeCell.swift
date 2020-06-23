import UIKit

class AssigneeCell: UITableViewCell {

    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var editButton: UIButton!
    @IBOutlet weak var assigneeTableView: UITableView!
    
    static func nib() -> UINib {
        return UINib(nibName: Identifier.Cell.assignee, bundle: nil)
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
        titleLabel.text = "Assignee"
        editButton.addTarget(self, action: #selector(editButtonPressed), for: .touchUpInside)
    }
    
    @objc private func editButtonPressed(_ sender: UIButton) {
        print(#function)
    }
    
}
