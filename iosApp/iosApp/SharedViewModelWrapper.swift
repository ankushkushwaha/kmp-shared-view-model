import shared

class SharedViewModelWrapper: ObservableObject {
    
    private var viewModel: SharedViewModel

    @Published var state: String = "123"
    @Published var isLoading: Bool = false

    init(viewModel: SharedViewModel = SharedViewModel()) {
        self.viewModel = viewModel
        observeCombinedState()
    }

    private func observeCombinedState() {
        // Observe combined state from Kotlin
        viewModel.observeCombinedState { [weak self] newState, newIsLoading in
            
            DispatchQueue.main.async {
                self?.state = newState
                self?.isLoading = newIsLoading.boolValue
                
                print("observeCombinedState")
            }
        }
    }

    // Methods to interact with the ViewModel
    func setState(_ newValue: String) {
        viewModel.setState(newValue: newValue)
    }

    func setLoading(loading: Bool) {
        viewModel.setLoading(loading: loading)
    }
    
    func fetchData() {
        viewModel.updateIsLoadingAfterDelay()
    }

    deinit {
        viewModel.clear()  // Cancel coroutines when the instance is destroyed
    }
}
