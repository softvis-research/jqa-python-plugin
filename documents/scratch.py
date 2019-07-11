import ast

ast_dump = ast.dump(ast.parse('src/main/resources/examples/http_server.py', 'filename', 'eval'))

print(ast_dump)
