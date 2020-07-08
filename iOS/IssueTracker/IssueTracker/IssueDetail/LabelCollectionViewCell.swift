import UIKit

class LabelCollectionViewCell: UICollectionViewCell {
    
    var cellConfiguration = "see ya" {
        didSet {
            setupCell()
        }
    }
//    @IBOutlet weak var nameLabel: UILabel!
    var nameLabel: UILabel = {
        return UILabel()
    }()
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCell()
    }
    
    static func nib() -> UINib {
        return UINib(nibName: String(describing: self), bundle: nil)
    }
    
    private func setupCell() {
        addSubview(nameLabel)
        nameLabel.text = cellConfiguration
    }
    
}
