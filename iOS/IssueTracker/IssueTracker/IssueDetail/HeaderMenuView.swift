import UIKit

class HeaderMenuView: UITableViewCell {
    
    var titleLabel = UILabel()
    var editButton = UIButton()

//    override init(frame: CGRect) {
//        super.init(frame: frame)
//        setupView()
//    }
    
    required init?(coder: NSCoder) {
        super.init(coder: coder)
        setupView()
    }
    
    private func setupView() {
        titleLabel.leadingAnchor.constraint(equalTo: self.leadingAnchor, constant: 20).isActive = true
        titleLabel.topAnchor.constraint(equalTo: self.topAnchor, constant: 0).isActive = true
        titleLabel.bottomAnchor.constraint(equalTo: self.bottomAnchor, constant: 0).isActive = true
        editButton.leadingAnchor.constraint(equalTo: self.trailingAnchor, constant: 20).isActive = true
        editButton.topAnchor.constraint(equalTo: self.topAnchor, constant: 0).isActive = true
        editButton.bottomAnchor.constraint(equalTo: self.bottomAnchor, constant: 0).isActive = true
    }
    
}
