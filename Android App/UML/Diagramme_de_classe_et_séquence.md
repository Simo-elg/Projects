## Diagramme de Classe 
@startuml

class AppCompatActivity{

}

class App extends DDB{
    +stock : ArrayList<Component>
    +client : ArrayList<Requester>
    +orders : ArrayList<Order>
    +dataBase : DDB
    +admin : Admin
    +stockkeeper : StockKeeper
    +assembler : Assembler
}

class SignUpActivity extends AppCompatActivity{
    +menu(View view) : void
}

class StockActivity extends AppCompatActivity{
    -populateTable() : void
    +deleteItemFromDatabase(int itemId) : void
    +back(View view) : void
}

class HardwareComponentActivity extends AppCompatActivity{
    +id_issued_by_storek : static int
    +type : String
    +sous_type_Input : TextInputEditText
    +descriptionInput : TextInputEditText
    +number_scrollInput : TextView
    +versionInput : TextInputEditText
    +licenseInput : TextInputEditText
    +compatibilityInput : TextInputEditText
    +priceInput : EditText

    +increment(View view) : void
    +decrement(View view) : void 
    +addComponent(View view) : void
}

class SoftwareComponentActivity extends AppCompatActivity{
    +id_issued_by_storek : static int
    +type : String
    +sous_type_Input : TextInputEditText
    +descriptionInput : TextInputEditText
    +number_scrollInput : TextView
    +versionInput : TextInputEditText
    +licenseInput : TextInputEditText
    +compatibilityInput : TextInputEditText
    +priceInput : EditText

    +increment(View view) : void
    +decrement(View view) : void 
    +addComponent(View view) : void
}

class CartActivity extends AppCompatActivity{
    -rvMyCart : RecyclerView
    -cart_adapter : CartAdapter
    -cartItems : List<CartModel>
    +db : DDB
    +id : String
}

class MainActivity extends AppCompatActivity{
    +idcli : static String
    +emailI : EditText
    +passwordI : EditText
    +login : Button
    +signUpButton : Button
    +db : DDB

    #onCreate(Bundle savedInstanceState) : void
}

class OrderActivity extends AppCompatActivity{
    -rvOrders : RecyclerView
    -orderAdapter : OrderAdapter
    -orders : List<OrderModel>
    -db : DDB
    +clientId : String

    #onCreate(Bundle savedInstanceState) : void
    +removeOrderFromClient(String clientId, String orderId, int position) : void
}

interface itemClickListner{
    +onClick(View view, int position, boolean isLongClick) : void
}

abstract class User extends AppCompatActivity implements Session {
    -nb_user : static int
    -email : String
    -id : int
    -firstName : String
    -lastName : String
    -password : String   
    -date_of_creation : Localdatetime
    -date_of_modification : Localdatetime
    +getUserName()
    +getId()
    +getFirstName()
    +getLastName()
    +getpassword()
    +getDateTimeCreation()
    +getModifDateTime()
    +setUserName(String userName)
    +updateTime(LocalDate d, LocalTime t)
    +setId(int i)
    +setFirstName(String p)
    +setLastName(String n)
    +setpassword(String m)
}

class Requester extends User {
    +cart : ArrayList<Component> 

    +Requester(String mail, String passcode, String fname, String lname, String Requester)
    +getEmail() : String
    +getPassword() : String
    +getFirstname : String
    +getLastname : String

    +setEmail(String mail) : void
    +setLastname(String new_l_name) : void
    +setPassword(String passcode) : void
    +setFirstname(String new_f_name) : void
}

class StorreKeeper extends User implements Staff {
    +created : boolean
    +stock : ArrayList<Component>

    +StoreKeeper(String mail,String passcode, String fname, String lname, String StoreKeeper)
    +getStock() : ArrayList<Component> 
    +etStock(ArrayList<Component> stock) : void
    +getEmail() : String
    +getPassword() : String
    +getFirstname : String
    +getLastname : String

    +setEmail(String mail) : void
    +setLastname(String new_l_name) : void
    +setPassword(String passcode) : void
    +setFirstname(String new_f_name) : void
}

class Stock extends Component{
    +Stock(int ide,String type1, String subType1, String description1, int quantite, String price)
    +getType() : String
    +getSubType() : String
    +getDescription() : String
    +getQuantity() : int
}

class Administrator extends User implements Staff {
    +clients : ArrayList<Requester>

    +Administrator(String mail, String passcode, String fname, String lname, String administrator)
    +getEmail() : String
    +getPassword() : String
    +getFirstname : String
    +getLastname : String

    +setEmail(String mail) : void
    +setLastname(String new_l_name) : void
    +setPassword(String passcode) : void
    +setFirstname(String new_f_name) : void
}

class Assembler extends User implements Staff {
    +created : boolean

    +Assembler(String mail,String passcode, String fname, String lname, String Assembler)
    +getEmail() : String
    +getPassword() : String
    +getFirstname : String
    +getLastname : String

    +setEmail(String mail) : void
    +setLastname(String new_l_name) : void
    +setPassword(String passcode) : void
    +setFirstname(String new_f_name) : void
}

class Order extends App {
    -nb_orders : static int
    -idComponents : ArrayList<Component>
    -state
    -orderId : int 
    -message : String
    -getMessage() : String
    -setMessage(String message) : void 
    +getOrderState() : String
    -addComponent(Component c) : void
    -remove(Component c) : void
    -getListComponent() : void
}

abstract class Component extends AppCompatActivity{
    +nb_components : int
    +id : int
    +name : String
    +type : String
    +quantite : int
    +sous_type : String
    +price : String

    +getId() : int
    +getType() : String
    +getSousType() : String 
    +getPrice() : String
    +getQuantite() : int
    +getDescription() : String

    +setType(String type) : void
    +setPrice(String price) : void
    +toString() : String
}

class HardwareComponent extends Component {
    -voltage : float
    -wattage : float 
    -dimensions : float 
}

class SoftwareComponent extends Component {
    -version : String
    -license : String
    -compatibility : String
}

HardwareComponent --|> Component
SoftwareComponent --|> Component


class DDB extends SQLiteOpenHelper{
    -DATABASE_NAME : static String
    -DATABASE_VERSION : static int
    +TABLE_STOCK : static String 
    +TABLE_STAFF : static String
    +TABLE_ORDERS : static String
    +TABLE_CLIENTS : static String

    +addIdToCart(String clientId, String compId) : boolean
    +addClient(User user) : long
    +addHComponent(HardwareComponent component) : boolean
    +addSComponent(SoftwareComponent component) : boolean
    +updateSoftwareComponent(int id_new, String soustype, String description, int quantity, String version, String license, String Compatibilite, String prix) : int
    +updateHardwareComponent(int id_n, String soustype, String description, int quantity, String voltage, String wattage, String dimensions, String price) : int
    +updateRequester(int id, String firstname, String lastname, String email, String password) : void
    +requesterExists(String email, String password) : String
    +deleteClient(String id) : int
    +deleteComponent(String clientId, String compId) : boolean
    +getClientData() : Cursor
    +deleteAllRequesters() : void
    +deleteAllStockItems() : void
    +removeOrderFromClient(String clientId, String orderIdToRemove) : boolean
    +deleteOrder(String orderId) : boolean
    +removeOrderIdFromList(String orders, String orderIdToRemove) : String
    +getProducts() : List<Products>
    +getCartIdsForClient(String clientId) : String
    +getCartItemsForClient(String clientId) : List<CartModel>
    +getOrdersByIds(List<String> orderIds) : List<OrderModel>
    +getOrderIdsForClient(String clientId) : List<String>
    +getStock() : List<Stock>
    +loadDataFromCsvFiles(Context context) : void
}

Requester "1" --> "*" Order : creates

StockKeeper "1" --> "*" Component : manages

Assembler "1" --> "*" Order : handles

AdminActivity -- DDB
AssemblerActivity -- DDB
CartActivity -- DDB
CreateOrderActivity -- DDB
HardwareComponentActivity -- DDB
SoftwareComponentActivity -- DDB
MainActivity -- DDB
RequesterActivity -- DDB
StockActivity -- DDB
SignUpActivity -- DDB


Order o-- Component
@enduml

@startuml
actor Requester
participant StockKeeper
participant Assembler
participant Order
participant Component
participant DataBase

Requester -> Order: CreateOrder()
Order -> DataBase: add Order


Requester -> Order: orderState()
Note right of Requester: "en attente d’acceptation"

Assembler -> StockKeeper: IsInComponent(Component c)
StockKeeper -> DataBase: ConsultComponent()
Note right of StockKeeper: Vérifie la disponibilité du Component
StockKeeper -> Assembler: Return Component status

alt Component disponible
    Assembler -> Requester: acceptOrder()
    Requester -> Order: orderState()
    Note right of Requester : "acceptée, en cours d’assemblage"
    Assembler -> Order: orderState()
    Order -> DataBase: orderState
    Note right of Order : update orderState
    Assembler -> StockKeeper : deleteComponent(Component c)
    StockKeeper -> Component: deleteComponent(Component c)
    Note right of StockKeeper: Retire le composant du Component
    Component -> DataBase: remove(Component c)
    Assembler -> Order: EncloseOrder()
    Order -> DataBase: update(Order)
    Requester -> Order: orderState()
    Note right of Requester: Met à jour l'état à "livrée"
else Component non disponible
    Assembler -> Requester: RejectOrder(Order)
    Order -> DataBase: update(Order)
    Requester -> Order: orderState()
    Note right of Requester: Met à jour l'état à "rejetée"
end

@enduml











