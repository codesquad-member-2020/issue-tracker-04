import UIKit

class PageTitleLabel: UILabel {
    static let fontSize: CGFloat = 30

    override func awakeFromNib() {
        super.awakeFromNib()

        configure()
    }

    private func configure() {
        self.font = .systemFont(ofSize: Self.fontSize)
    }
}
