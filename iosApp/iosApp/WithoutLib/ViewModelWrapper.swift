//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Ankush Kushwaha on 11/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Shared

class ViewModelWrapper: ObservableObject {
    
    @Published var sharedViewModel = LoginSharedViewModel()
    
    @Published var showDetailView: Bool = false

    @Published var state: ViewState?
    
    init() {
        observeCombinedState()
    }
    
    private func observeCombinedState() {

        sharedViewModel.observeCombinedState { [weak self] viewState in
            
            DispatchQueue.main.async {
                print("observeCombinedState")
                self?.state = viewState
                self?.showDetailView = viewState.pushToHomeScreen?.boolValue ?? false
            }
        }
    }
    
    func updateState(newState: ViewState?) {
        guard let state = newState else {
            return
        }
        sharedViewModel.updateState(newState: state)
    }
    
    func startFetch() {
        sharedViewModel.startFetch()
    }
    
    //    deinit {
    //        sharedViewModel.clear()  // Cancel coroutines when the instance is destroyed
    //    }

}
