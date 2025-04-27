from collections import defaultdict
import requests
import json
# from fastapi import FastAPI

# app = FastAPI()

# # 1
# @app.get("/items/{item_id}")
# async def read_item(item_id):
#     return {"item_id": item_id, "description" : "sample item"}


#2
def process_dict(dictio : list[dict])->dict:
    processed = defaultdict(list)
    for d in dictio:
        if d.get('role', 0) != 0 and d.get('name', 0) != 0:
            processed[d['role']].append(d['name'])
            # assumed that names are allowed to be duplicate in the list.
    return processed

#3
def make_requests():
    url = 'https://jsonplaceholder.typicode.com/posts/1'
    try:
        resp = requests.get(url, timeout=5)
        resp.raise_for_status()
        data = resp.json()
        # print(type(data), data)
        return data

    except requests.ConnectionError as e:
        print("Connection Error", e)
    except requests.exceptions.Timeout as e:
        print("Timeout Error", e)
    except requests.exceptions.HTTPError as e:
        print("HTTP Error", e)
    except requests.exceptions.RequestException as e:
        print(f"Error: An unexpected error occurred: {e}")
    except json.JSONDecodeError as e:
        print(f"Error: Could not decode the JSON response. Details: {e}")

#4
# def SQLAlchemy

if __name__ == '__main__':
    # testval = [{'id': 1, 'name': 'Alice', 'role': 'admin'}, {'id': 1, 'name': 'Bob', 'role': 'admin'}, {'id': 1, 'name': 'Bob', 'role': 'b'}, {'id': 1, 'name': 'Alice', 'role': 'a'}]
    # print(process_dict(testval))
    print(make_requests())