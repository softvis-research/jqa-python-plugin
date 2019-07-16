import ast

ast_dump = ast.dump(ast.parse('src/test/resources/example/http_server.py', 'filename', 'eval'))

print(ast_dump)
