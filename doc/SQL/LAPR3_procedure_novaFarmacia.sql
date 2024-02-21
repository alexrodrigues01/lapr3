CREATE OR REPLACE PROCEDURE prcNovaFarmacia(p_email Farmacia.email%TYPE, p_nome Farmacia.nome%TYPE, p_nif Farmacia.nif%TYPE, p_telefone Farmacia.telefone%TYPE, p_lugaresComSuporte int, p_lugaresSemSuporte int,p_capacidadeBateria int, p_morada Morada.morada%TYPE, p_latitude Morada.latitude%TYPE, p_longitude Morada.longitude%TYPE, p_altitude Morada.altitude%TYPE, p_email_gestor GestorFarmacia.emailGestor%TYPE) IS

	v_id_morada Morada.id%TYPE;
	v_id_farmacia Farmacia.id%TYPE;
	v_id_parqueEstacionamento ParqueEstacionamento.id%TYPE;
    v_lugares int;
    v_lastIDEstacionamento LugarEstacionamento.id%TYPE;

begin

	SELECT id INTO v_id_morada
	FROM Morada
	WHERE morada = p_morada AND longitude = p_longitude AND latitude = p_latitude AND altitude = p_altitude;

	-- obtem o ultimo id de farmacia para que a nova farmacia seja criada com o id consecutivo
	SELECT MAX(id) INTO v_id_farmacia
	FROM Farmacia;

	IF v_id_farmacia IS NULL
	THEN

		v_id_farmacia := 1;

	ELSE

		v_id_farmacia := v_id_farmacia + 1;

	END IF;

	INSERT INTO Farmacia (id, nome, nif, telefone, email, emailGestor, id_morada) values(v_id_farmacia, p_nome, p_nif, p_telefone, p_email, p_email_gestor, v_id_morada);

	SELECT MAX(id) INTO v_id_parqueEstacionamento
	FROM LugarEstacionamento;

	IF v_id_parqueEstacionamento IS NULL
	THEN

		v_id_parqueEstacionamento := 1;

	ELSE

		v_id_parqueEstacionamento := v_id_parqueEstacionamento + 1;

	END IF;

	INSERT INTO ParqueEstacionamento (id,capacidadeEnergia,id_farmacia,id_tipoEstacionamento) values (v_id_parqueEstacionamento,p_capacidadeBateria,v_id_farmacia,1);

    --vai buscar o ultimo id do estacionamento
    SELECT MAX(id) INTO v_lastIDEstacionamento
    FROM LugarEstacionamento;
    
    IF v_lastIDEstacionamento IS NULL
    THEN v_lastIDEstacionamento:=0;
    END IF;
    
    -- cria os lugares de estacionamento com suporte para carregamento
    v_lugares := p_lugaresComSuporte;
    
    LOOP
    
        EXIT WHEN v_lugares = 0;
    
        v_lugares := v_lugares -1;
    
        v_lastIDEstacionamento:=v_lastIDEstacionamento+1;
    
        INSERT INTO LugarEstacionamento (id,id_suporteCarregamento,id_parqueEstacionamento) values(v_lastIDEstacionamento,1,v_id_parqueEstacionamento);
    
    
    END LOOP;
    
    -- cria os lugares de estacionamento sem suporte para carregamento
    v_lugares := p_lugaresSemSuporte;

    LOOP
    
        EXIT WHEN v_lugares = 0;

        v_lugares := v_lugares -1;

        v_lastIDEstacionamento:=v_lastIDEstacionamento+1;
    
        INSERT INTO LugarEstacionamento (id,id_suporteCarregamento,id_parqueEstacionamento) values(v_lastIDEstacionamento,2,v_id_parqueEstacionamento);
    
    END LOOP;

end;
