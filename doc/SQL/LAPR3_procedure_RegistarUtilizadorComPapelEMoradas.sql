CREATE OR REPLACE PROCEDURE prcUtilizadorMorada(p_email utilizador.email%type, p_password utilizador.password%type, p_papel papelutilizador.id%type, p_nome utilizador.nome%type, p_nif utilizador.nif%type, p_telefone utilizador.telefone%type, p_morada morada.morada%type, p_latitude morada.latitude%type, p_longitude morada.longitude%type, p_altitude Morada.altitude%TYPE) IS

	v_id_morada Morada.id%TYPE;

begin
    
	SELECT id INTO v_id_morada 
	FROM Morada 
	WHERE morada = p_morada AND longitude = p_longitude AND latitude = p_latitude AND altitude = p_altitude;

	INSERT INTO Utilizador (email, password, nome, nif, telefone, id_morada, id_papel) values(p_email, p_password, p_nome, p_nif, p_telefone, v_id_morada, p_papel);
	
end;
