import SwiftUI
import shared

struct ContentView: View {
    var body: some View {
        NavigationStack {
            VStack {
            
                NavigationLink(destination: DetailView()) {
                    Text("Go to Detail View")
                        .font(.title2)
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(10)
                }
            }
            .navigationTitle("Main View")
        }
    }
}


struct DetailView: View {
    @StateObject private var viewModel = SharedViewModelWrapper()
    
    var body: some View {
        
        Text(viewModel.viewState.message ?? "")
            .padding(20)
        
        Button("Update Message") {
            viewModel.setMessage("Messsage: \(Date().timeIntervalSince1970.description)" )
        }
        .padding(.bottom, 100)
        
        
        Text("\(viewModel.viewState.count)")
            .padding(20)
        
        Button("Increment") {
            viewModel.incrementCount()
        }
        .padding(.bottom, 100)
        

        
        
        if viewModel.isLoading {
            ProgressView("Loading...")
                .padding()
        } else {
            Text("Data fetched successfully!")
                .padding()
        }
        
        Button("Fetch data") {
            viewModel.fetchData()
        }
        .padding()
        
        Button("Toggle Loading") {
            viewModel.setLoading(loading: !viewModel.isLoading)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
