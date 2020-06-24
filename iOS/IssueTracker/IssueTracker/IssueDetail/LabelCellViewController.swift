import UIKit

class LabelCellViewController: UIViewController {
    
    var labels = ["1","2","3"]
    private var dataSource = LabelCollectionViewDataSource()
    @IBOutlet var labelCollectionView: UICollectionView!
    override func viewDidLoad() {
        labelCollectionView.dataSource = dataSource
    }
}

class LabelCollectionViewDataSource: NSObject, UICollectionViewDataSource {
    
    var labels = ["a","b","c"]
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return labels.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "LabelCollectionViewCell", for: indexPath)
        cell.backgroundColor = .red
        cell.cornerRadius = 15
        
        return cell
    }
    
}
