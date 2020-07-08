import UIKit

class CommentCell: UITableViewCell {
    
//    var configurator
    @IBOutlet weak var profileImageView: UIImageView!
    @IBOutlet weak var authorIdLabel: UILabel!
    @IBOutlet weak var bodyLabel: UITextView!
    @IBOutlet weak var timeLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
}
