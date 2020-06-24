import UIKit

class LabelCollectionViewCell: UICollectionViewCell {
    
    var cellConfiguration =
    @IBOutlet weak var nameLabel: UILabel!
    
    override func awakeFromNib() {
        super.awakeFromNib()
        setupCell()
    }
    
    private func setupCell() {
        
    }
    
}
