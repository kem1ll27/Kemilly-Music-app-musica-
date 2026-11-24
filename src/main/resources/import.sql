-- Dados iniciais (MySQL / Laragon)
-- Colunas em camelCase, iguais aos campos Java das entidades (sem @Column)

-- Perfis obrigatórios
insert into perfil_usuario (id, nome) values (1, 'USER');
insert into perfil_usuario (id, nome) values (2, 'ADMIN');

-- Admin de teste: admin@kemillymusic.com / admin123
insert into usuario (id, nome, email, senha, perfil_usuario_id)
values (1, 'Administrador', 'admin@kemillymusic.com', 'admin123', 2);

-- Usuário comum: ana@kemillymusic.com / 123456
insert into usuario (id, nome, email, senha, perfil_usuario_id)
values (2, 'Ana', 'ana@kemillymusic.com', '123456', 1);

-- Músicas iniciais
insert into musica (id, titulo, artista, imagemUrl, linkUrl, categoria) values
                                                                            (1, 'Duro Igual Concreto', '1Kilo', 'https://i.pinimg.com/736x/6f/b5/cd/6fb5cde9532c38059a3cd5b567f9476c.jpg', 'https://youtu.be/vTKP99Kll5E', 'RECENTE'),
                                                                            (2, 'Nights', 'Frank Ocean', 'https://i.pinimg.com/736x/c7/24/b9/c724b9f22443ef3ecd4785b63e39d885.jpg', 'https://youtu.be/r4l9bFqgMaQ', 'RECENTE'),
                                                                            (3, 'Quero saber', 'AJULLIACOSTA', 'https://i.pinimg.com/736x/ed/61/44/ed614459fbcbaa383d7f9743a8b81b2f.jpg', 'https://youtu.be/9fLyjCyqUAo', 'MAIS_OUVIDA'),
                                                                            (4, 'Hora H', 'BUDAH', 'https://i.pinimg.com/736x/2b/82/b3/2b82b3feb4b991c51a303893f839cae0.jpg', 'https://youtu.be/AOi_M500EBc', 'MAIS_OUVIDA');

-- Playlists iniciais
insert into playlist (id, titulo, descricao, imagemUrl, linkUrl) values
                                                                     (1, 'As mais mais', 'Varios artistas', 'https://i.pinimg.com/736x/ee/59/6f/ee596f6c478f2851878f0d8264327935.jpg', 'https://www.youtube.com/watch?v=P6mNggaNaTA'),
                                                                     (2, 'Trap br', 'Varios artistas', 'https://i.pinimg.com/736x/20/30/ab/2030ab15b2069ee4d4cd7e1e3204cfb6.jpg', 'https://www.youtube.com/watch?v=SousDVMKHLc');

-- Ajusta AUTO_INCREMENT para nao conflitar com IDs manuais
ALTER TABLE perfil_usuario AUTO_INCREMENT = 3;
ALTER TABLE usuario AUTO_INCREMENT = 3;
ALTER TABLE musica AUTO_INCREMENT = 5;
ALTER TABLE playlist AUTO_INCREMENT = 3;