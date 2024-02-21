CREATE OR REPLACE FUNCTION fncNovaEncomenda(p_farmacia Farmacia.id%TYPE, p_cliente Cliente.emailCliente%TYPE, p_carga Encomenda.carga%TYPE) RETURN Encomenda.id%TYPE IS

	v_id_encomenda Encomenda.id%TYPE;

BEGIN

-- obtencao do id da encomenda
	SELECT MAX(id) INTO v_id_encomenda
	FROM Encomenda;

	IF v_id_encomenda IS NULL
	THEN
		v_id_encomenda := 1;
	ELSE
		v_id_encomenda := v_id_encomenda + 1;
	END IF;

	INSERT INTO Encomenda (id, carga, emailCliente, id_farmacia) VALUES (v_id_encomenda, p_carga, p_cliente, p_farmacia);

	RETURN v_id_encomenda;

END;
