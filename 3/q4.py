from sqlalchemy import create_engine, Column, Integer,String, Double
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker
from faker import Faker
#script
base = declarative_base()
class Product(base):
    __tablename__ = 'product'
    id = Column(Integer, primary_key=True)
    name = Column(String(50), nullable=False)
    price = Column(Double, nullable=False)

    def __repr__(self):
        return f"<Product(id={self.id}, name='{self.name}', price='{self.price}')>"


engine = create_engine("sqlite+pysqlite:///:memory:", echo=True)

base.metadata.create_all(engine)

session = sessionmaker(bind=engine)
db = session()


fake = Faker()

def generate_mock_product(db, num = 15):
    products = []
    products.append(Product(name="Exclude", price=10.0)) # query will always exclude this
    for _ in range(num):
        name = fake.name()
        price = fake.random_number(digits=4,fix_len=False)
        product = Product(name=name, price=price)
        products.append(product)  
    db.add_all(products)
    db.commit()
    return products


print("RAW", generate_mock_product(db))
for prod in db.query(Product).all():
    print(prod)


#ORM Query

def get_product_by_pricerange(session, price=100, ) -> list:
    products = []
    for product in session.query(Product).filter(Product.price > 100):
        products.append(product)
        print(product)
    return products

get_product_by_pricerange(session=db)