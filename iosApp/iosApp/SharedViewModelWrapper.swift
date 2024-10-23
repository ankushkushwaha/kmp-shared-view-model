import shared

class SharedViewModelWrapper: ObservableObject {
    
    private var viewModel: SharedViewModel

    @Published var isLoading: Bool = false
    @Published var viewState: ViewState = ViewState.companion.create()

    init(viewModel: SharedViewModel = SharedViewModel()) {
        self.viewModel = viewModel
        observeCombinedState()
        
    }

    private func observeCombinedState() {
        // Observe combined state from Kotlin
        viewModel.observeCombinedState { [weak self] isLoading, viewState in
            
            DispatchQueue.main.async {
                self?.isLoading = isLoading.boolValue
                self?.viewState = viewState

                print("observeCombinedState")
            }
        }
    }

    func setMessage(_ message: String) {
        viewModel.setMessage(newMessage: message)
    }
    
    func incrementCount() {
        let count = viewState.count as! Int + 1
        viewModel.setCount(count: Int32(count))
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
